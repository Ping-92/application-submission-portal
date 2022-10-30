package com.cognixia.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.common.exception.ApplicationNotFoundException;
import com.cognixia.model.Application;
import com.cognixia.repository.ApplicationRepository;

@Service
public class ApplicationService {
	@Autowired
	private ApplicationRepository applicationRepository;
	
	// retrieve full list of applications
	public List<Application> getAllApplications(){
		return applicationRepository.findAll();
	}
	
	// add one application to database
	public Application addApplication(Application application) {
		application.setSubmissionDateTime(LocalDateTime.now());
		application.setApplicationStatus("Submitted");
		return applicationRepository.save(application);
	}
	
	// retrieve one application by searching for application id
	public Application getApplicationById(int applicationId) {
		return applicationRepository.findById(applicationId).orElseThrow(ApplicationNotFoundException::new);
	}

}
