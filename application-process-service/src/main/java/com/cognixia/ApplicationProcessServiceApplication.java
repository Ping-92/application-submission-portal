package com.cognixia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableEurekaClient
@EnableFeignClients
@IntegrationComponentScan
@SpringBootApplication
@EnableScheduling
public class ApplicationProcessServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationProcessServiceApplication.class, args);
	}

}
