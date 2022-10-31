package com.cognixia.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
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

@Configuration
@EnableBatchProcessing
public class ApplicationProcessingBatch {
	
	@Autowired
	private DataSource dataSource;

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	public JsonItemReader<Application> jsonItemReader() {
        return new JsonItemReaderBuilder<Application>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(Application.class))
                .resource(new ClassPathResource("submissions.json"))
                .name("applicantJsonItemReader")
                .build();
    }
	
	@Bean
    public ApplicationItemProcessor processor() {
        return new ApplicationItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Application> writer() {
        JdbcBatchItemWriter<Application> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("insert into notificationdb(application_id,email) values (:applicationId, :user.email)");
        writer.setDataSource(dataSource);
        return writer;
    }
}
