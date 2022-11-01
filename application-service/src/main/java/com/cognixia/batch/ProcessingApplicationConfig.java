package com.cognixia.batch;

import java.nio.file.Path;
import java.nio.file.Paths;

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
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.cognixia.model.Application;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


@EnableBatchProcessing
@Configuration
public class ProcessingApplicationConfig {
	@Autowired
    private DataSource dataSource;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    // reading from json file
    public JsonItemReader<Application> jsonItemReader() {
        return new JsonItemReaderBuilder<Application>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(Application.class))
                .resource(new ClassPathResource("submissions.json"))
                .name("applicationJsonItemReader")
                .build();
    }
    
    // writing to json file
	public JsonFileItemWriter<Application> jsonFileItemWriter() {
		ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		Path currentRelativePath = Paths.get("");
		String abs = currentRelativePath.toAbsolutePath().toString();
		
		return new JsonFileItemWriterBuilder<Application>()
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>(objectMapper))
                .resource(new FileSystemResource(abs+"/src/main/resources/submissions.json"))
                .name("applicationJsonItemWriter")
                .build();
	}
    
    @Bean
    public ProcessingApplicationProcessor processor() {
        return new ProcessingApplicationProcessor();
    }
    
    // read from db
    @Bean 
	public JdbcCursorItemReader<Application> jdbcCursorItemReader() {
		JdbcCursorItemReader<Application> jdbcCursorItemReader = new JdbcCursorItemReader<>();

		jdbcCursorItemReader.setDataSource(dataSource);
		jdbcCursorItemReader.setSql("SELECT * from application;");
		jdbcCursorItemReader.setRowMapper(new BeanPropertyRowMapper<Application>() {
			{
				setMappedClass(Application.class);
			}
		});
		return jdbcCursorItemReader;
	}

    // write to db
    @Bean
    public JdbcBatchItemWriter<Application> writer() {
        JdbcBatchItemWriter<Application> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("insert into perm_application(application_id, application_status, country_of_birth, date_of_birth, name, race, submission_date_time, user_id, vaccination_status) values (:applicationId,:applicationStatus,:countryOfBirth,:dateOfBirth,:name,:race,:submissionDateTime,:userId,:vaccinationStatus)");
        writer.setDataSource(dataSource);
        return writer;
    }
    
    // start of first job
    @Bean
    public Job writeApplicationDataIntoJson() {
    	return jobBuilderFactory.get("writeApplicationDataIntoJson")
				.incrementer(new RunIdIncrementer())
				.start(getFirstStep())
				.build();
    }
    
    @Bean
    public Step getFirstStep() {
    	return stepBuilderFactory.get("getFirstStep")
				.<Application, Application>chunk(1)
				.reader(jdbcCursorItemReader())
				.writer(jsonFileItemWriter())
				.build();
    }
    // end of first job
    
    // start of second job
    @Bean
    public Job writeApplicationDataIntoSqlDb() {
        JobBuilder jobBuilder = jobBuilderFactory.get("writeApplicationDataIntoSqlDb");
        jobBuilder.incrementer(new RunIdIncrementer());
        FlowJobBuilder flowJobBuilder = jobBuilder.flow(getSecondStep()).end();
        Job job = flowJobBuilder.build();
        return job;
    }

    @Bean
    public Step getSecondStep() {
        StepBuilder stepBuilder = stepBuilderFactory.get("getSecondStep");
        SimpleStepBuilder<Application, Application> simpleStepBuilder = stepBuilder.chunk(1);
        return simpleStepBuilder.reader(jsonItemReader()).processor(processor()).writer(writer()).build();
    }
    // end of second job
    
}
