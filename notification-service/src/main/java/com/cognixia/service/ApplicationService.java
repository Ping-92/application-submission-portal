package com.cognixia.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cognixia.model.Application;

@Service
@FeignClient(name="APPLICATION-SERVICE")
public interface ApplicationService {
	
	@GetMapping("/applicant/{applicationid}")
	public Application getApplicationById(@PathVariable("applicationid") int applicationId);
	
	@GetMapping("/applicant")
	public List<Application> getAllApplications();
	
}
