package com.cognixia.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cognixia.model.Application;
import com.cognixia.service.ApplicationService;

@RestController
@RequestMapping("/application")
public class ApplicationController {
	@Autowired
	ApplicationService applicationService;
	
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
	
//	@GetMapping("/writetofile")
//	public ResponseEntity<String> writeToFileAndUpload(){
//		if(applicationService.writeToFileAndUpload()) 
//			return ResponseEntity.ok("Written to json file. File upload completed");
//		else
//			return ResponseEntity.notFound().build(); 
//	}
//
	
// JSON Reading Related
//	@GetMapping("/listfromjson") 
//	public Iterable<Application> listFromJson(){
//		return applicationService.listFromJson();
//	}
}
