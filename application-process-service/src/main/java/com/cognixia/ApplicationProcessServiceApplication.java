package com.cognixia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.integration.annotation.IntegrationComponentScan;

@EnableEurekaClient
@EnableFeignClients
@IntegrationComponentScan
@SpringBootApplication
public class ApplicationProcessServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationProcessServiceApplication.class, args);
	}

}
