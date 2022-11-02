package com.cognixia.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.cognixia.model.PermApplication;

@Service
@FeignClient(name="APPLICATION-SERVICE")
public interface PermApplicationService {
	
	// get all permApplication
	@GetMapping("/permapplication/")
	public List<PermApplication> getAllPermApplications();
	
	// get by permApplication Id
	@GetMapping("/permapplication/{applicationid}")
	public PermApplication getPermApplicationById(int applicationId);
	
	// update permApplication
	@PutMapping("/permapplication/{applicationid}")
	public PermApplication updatePermApplication(PermApplication permApplication);
}