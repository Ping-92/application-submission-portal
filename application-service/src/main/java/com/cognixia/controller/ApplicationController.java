package com.cognixia.controller;

import java.net.URI;
import java.util.List;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cognixia.common.exception.ApplicationIDMismatchException;
import com.cognixia.model.Application;
import com.cognixia.model.PermApplication;
import com.cognixia.service.ApplicationService;

@RestController
@RequestMapping("/application")
public class ApplicationController {
	@Autowired
	private ApplicationService applicationService;
	
	@GetMapping
	public ResponseEntity<List<Application>> getAllApplications(){
		return ResponseEntity.ok(applicationService.getAllApplications());
	}
	
	@PostMapping("/applicant")
	public ResponseEntity<Application> addApplication(@RequestBody @Valid Application application){
		Application newApplication = applicationService.addApplication(application);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{applicationid}")
				.buildAndExpand(newApplication.getApplicationId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/applicant/{applicationid}")
	public ResponseEntity<Application> getApplicationById(@PathVariable("applicationid") int applicationId){
		Application application = applicationService.getApplicationById(applicationId);
		if (application == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(application);
		}
	}
	
	// get all permApplication
	@GetMapping("/permapplication/")
	public ResponseEntity<List<PermApplication>> getAllPermApplications(){
		return ResponseEntity.ok(applicationService.getAllPermApplications());
	}
	
	// get by permApplication Id
	@GetMapping("/permapplication/{applicationid}")
	public ResponseEntity<PermApplication> getPermApplicationById(@PathVariable("applicationid") int applicationId){
		PermApplication permApplication = applicationService.getPermApplicationById(applicationId);
		if (permApplication == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(permApplication);
		}
	}
	
	// update permApplication
	@PutMapping("/permapplication/{applicationid}")
	public ResponseEntity<PermApplication> updatePermApplication(@PathVariable int applicationid, @Valid @RequestBody PermApplication permApplication) {
		if (applicationid != permApplication.getApplicationId()) {
			throw new ApplicationIDMismatchException("IDs do not match!");
		}
		PermApplication updatedPermApplication = applicationService.updatePermApplication(permApplication);
		if (updatedPermApplication == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(updatedPermApplication);
		}
	}
	
	// Batch Job operation
	@GetMapping("/job/start/{jobName}")
	public ResponseEntity<String> startJob(@PathVariable String jobName) {
		if(applicationService.startJob(jobName)) {
			return ResponseEntity.ok("Job Started.....");
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	 //truncate table
	@GetMapping("/truncate")
	public ResponseEntity<String> fileUpload(){
		if(applicationService.removeAllApplication()) 
			return ResponseEntity.ok("Truncated Table...");
		else
			return ResponseEntity.notFound().build(); 
	}
}
