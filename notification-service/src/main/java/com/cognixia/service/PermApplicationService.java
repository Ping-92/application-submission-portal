package com.cognixia.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.cognixia.model.PermApplication;

@Service
@FeignClient(name="APPLICATION-SERVICE")
public interface PermApplicationService {
	
	@GetMapping("/permapplication/")
	public List<PermApplication> getAllPermApplications();
	
}
