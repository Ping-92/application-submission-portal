package com.cognixia.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


import com.cognixia.SftpConfig.UploadGateway;
import com.cognixia.common.LocalDateSerializer;
import com.cognixia.common.LocalDateTimeSerializer;
import com.cognixia.common.exception.ApplicationNotFoundException;
import com.cognixia.model.Application;
import com.cognixia.repository.ApplicationRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class ApplicationService {
	private ApplicationRepository applicationRepository;
	
	private UsersService usersService;
	
	private UploadGateway gateway;
	
	@Autowired
	public ApplicationService(ApplicationRepository applicationRepository, UploadGateway gateway, @Lazy UsersService usersService) {
		this.applicationRepository = applicationRepository;
		this.usersService = usersService;
		this.gateway = gateway;
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
	
	// write all applications to file
	@Transactional
	public boolean writeToFileAndUpload() {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("submissions.json"))){
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
			gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
			Gson gson = gsonBuilder.setPrettyPrinting().create();
			List<Application> appList = getAllApplications();
			gson.toJson(appList, bw);
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		gateway.upload(new File("submissions.json"));
		
		applicationRepository.truncateApplicationTable();
		return true;	
	}

	// Read From JSON Related
	public Iterable<Application> listFromJson(){
		return applicationRepository.findAll();
	}
	
	public Application save(Application application) {
		return applicationRepository.save(application);
	}
	
	public Iterable<Application> save(List<Application> applications) {
		return applicationRepository.saveAll(applications);
	}

}
