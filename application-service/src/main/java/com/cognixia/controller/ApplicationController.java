package com.cognixia.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.model.Application;
import com.cognixia.service.ApplicationService;

@RestController
@RequestMapping("/application")
public class ApplicationController {
	@Autowired
	ApplicationService applicationService;
	
	//TODO complete and add necessary methods
	
//	@PostMapping("/applicant")
//	public ResponseEntity<Application> addApplication(@RequestBody @Valid Application application){
//		
//	}
//	
//	@GetMapping("{applicationId}")
//	public ResponseEntity<Application> getApplicationById(int applicationId){}
}
