package com.cognixia.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.cognixia.common.exception.ApplicationNotFoundException;
import com.cognixia.model.Application;
import com.cognixia.repository.ApplicationRepository;

@Service
public class ApplicationService {
	private ApplicationRepository applicationRepository;

	private UsersService usersService;
	
	@Autowired
	public ApplicationService(ApplicationRepository applicationRepository, @Lazy UsersService usersService) {
		this.applicationRepository = applicationRepository;
		this.usersService = usersService;
	}
	
	
	// retrieve full list of applications
	public List<Application> getAllApplications(){
		List<Application> appList = applicationRepository.findAll();
		for (Application application: appList) {
			application.setUser(usersService.getUserById(application.getUserId()));
		}
		return appList;
	}
	
	// add one application to database
	public Application addApplication(Application application) {
		application.setSubmissionDateTime(LocalDateTime.now());
		application.setApplicationStatus("Submitted");
		int userId = usersService.getCurrentUserId();
		application.setUserId(userId);
		application.setUser(usersService.getUserById(userId));
		return applicationRepository.save(application);
	}
	
	// retrieve one application by searching for application id
	public Application getApplicationById(int applicationId) {
		Application application = applicationRepository.findById(applicationId).orElseThrow(ApplicationNotFoundException::new);
		if (application != null) {
			application.setUser(usersService.getUserById(application.getUserId()));
		}
		return application;
	}
}
