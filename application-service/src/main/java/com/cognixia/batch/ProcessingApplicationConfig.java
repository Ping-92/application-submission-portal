package com.cognixia.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowJobBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.cognixia.model.Application;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@EnableBatchProcessing
@Configuration
public class ProcessingApplicationConfig {
	@Autowired
    private DataSource dataSource;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    public JsonItemReader<Application> jsonItemReader() {
        return new JsonItemReaderBuilder<Application>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(Application.class))
                .resource(new ClassPathResource("submissions.json"))
                .name("applicationJsonItemReader")
                .build();
    }
    
    @Bean
    public ProcessingApplicationProcessor processor() {
        return new ProcessingApplicationProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Application> writer() {
        JdbcBatchItemWriter<Application> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("insert into perm_application(application_id, application_status, country_of_birth, date_of_birth, name, race, submission_date_time, user_id, vaccination_status) values (:applicationId,:applicationStatus,:countryOfBirth,:dateOfBirth,:name,:race,:submissionDateTime,:userId,:vaccinationStatus)");
        writer.setDataSource(dataSource);
        return writer;
    }
    
    @Bean
    public Job writeStudentDataIntoSqlDb() {
        JobBuilder jobBuilder = jobBuilderFactory.get("writeStudentDataIntoSqlDb");
        jobBuilder.incrementer(new RunIdIncrementer());
        FlowJobBuilder flowJobBuilder = jobBuilder.flow(getFirstStep()).end();
        Job job = flowJobBuilder.build();
        return job;
    }

    @Bean
    public Step getFirstStep() {
        StepBuilder stepBuilder = stepBuilderFactory.get("getFirstStep");
        SimpleStepBuilder<Application, Application> simpleStepBuilder = stepBuilder.chunk(1);
        return simpleStepBuilder.reader(jsonItemReader()).processor(processor()).writer(writer()).build();
    }
}
