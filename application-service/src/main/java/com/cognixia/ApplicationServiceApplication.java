package com.cognixia;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.jdbc.core.JdbcTemplate;

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
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationServiceApplication.class, args);
	}

	// for reading from JSON File.
	@Bean
	CommandLineRunner runner(ApplicationService applicationService) {
		return args -> {
			// read json and write to db
			ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
			TypeReference<List<Application>> typeReference = new TypeReference<List<Application>>() {
			};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/submissions.json");
			try {
				List<Application> applications = mapper.readValue(inputStream, typeReference);
				for (Application a : applications) {
					jdbcTemplate.update("INSERT INTO perm_application VALUES (?,?,?,?,?,?,?,?,?)", a.getApplicationId(),
							a.getApplicationStatus(), a.getCountryOfBirth(), a.getDateOfBirth(), a.getName(),
							a.getRace(), a.getSubmissionDateTime(), a.getUserId(), a.getVaccinationStatus());

				}
				System.out.println("Applications Saved In Perm");
			} catch (IOException e) {
				System.out.println("Unable to save applications: " + e.getMessage());
			}
		};
	}

}
