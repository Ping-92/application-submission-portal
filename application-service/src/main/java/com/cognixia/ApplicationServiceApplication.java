package com.cognixia;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

import com.cognixia.model.Application;
import com.cognixia.service.ApplicationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@IntegrationComponentScan
@EnableIntegration
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class ApplicationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationServiceApplication.class, args);
	}
	
	@Bean
	CommandLineRunner runner(ApplicationService applicationService) {
		return args -> {
			// read json and write to db
			ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());;
			TypeReference<List<Application>> typeReference = new TypeReference<List<Application>>() {};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/submissions.json");
			try {
				List<Application> applications = mapper.readValue(inputStream, typeReference);
				applicationService.save(applications);
				System.out.println("Applications Saved In Perm");
			} catch(IOException e) {
				System.out.println("Unable to save applications: " + e.getMessage());
			}
		};
	}

}
