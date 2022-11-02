package com.cognixia.service;

//import java.io.BufferedWriter;
import java.io.File;
//import java.io.FileWriter;
//import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.cognixia.SftpConfig.UploadGateway;
//import com.cognixia.common.LocalDateSerializer;
//import com.cognixia.common.LocalDateTimeSerializer;
import com.cognixia.common.exception.ApplicationNotFoundException;
import com.cognixia.model.Application;
import com.cognixia.model.PermApplication;
import com.cognixia.repository.ApplicationRepository;
import com.cognixia.repository.PermApplicationRepository;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;

@Service
public class ApplicationService {
	private ApplicationRepository applicationRepository;
	
	private PermApplicationRepository permApplicationRepository;
	
	private UsersService usersService;
	
	private UploadGateway gateway;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Qualifier("writeApplicationDataIntoJson")
	@Autowired
	Job writeApplicationDataIntoJson;
	
	@Qualifier("writeApplicationDataIntoSqlDb")
	@Autowired
	Job writeApplicationDataIntoSqlDb;

	
	
	@Autowired
	public ApplicationService(
			ApplicationRepository applicationRepository, PermApplicationRepository permApplicationRepository, 
			UploadGateway gateway, @Lazy UsersService usersService) {
		this.applicationRepository = applicationRepository;
		this.usersService = usersService;
		this.gateway = gateway;
		this.permApplicationRepository = permApplicationRepository;
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
	
	//batch job runner
	public boolean startJob(String jobName) {
		Map<String, JobParameter> params = new HashMap<>();
		
		// put current time in millseconds so current job will be unique everytime
		params.put("currentTime", new JobParameter(System.currentTimeMillis()));
		
		JobParameters jobParameters = new JobParameters(params);
		try {
			if (jobName.equals("writeApplicationDataIntoJson")) {
				jobLauncher.run(writeApplicationDataIntoJson, jobParameters);
				return true;
				
			} else if (jobName.equals("writeApplicationDataIntoSqlDb")) {
				jobLauncher.run(writeApplicationDataIntoSqlDb, jobParameters);
				return true;
			}
			
		} catch (JobExecutionAlreadyRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobRestartException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	// upload file to sftp and truncate table
	@Transactional
	public boolean fileUpload() {
		ClassLoader classLoader = getClass().getClassLoader();
		gateway.upload(new File(classLoader.getResource("submissions.json").getFile()));
		
		applicationRepository.truncateApplicationTable();
		return true;	
	}
	
	// start of PermApplication methods
	// retrieve one PermApplication by searching for application id
	public PermApplication getPermApplicationById(int applicationId) {
		PermApplication permApplication = permApplicationRepository.findById(applicationId)
				.orElseThrow(ApplicationNotFoundException::new);
		
		if (permApplication != null) {
			permApplication.setUser(usersService.getUserById(permApplication.getUserId()));
		}
		return permApplication;
	}

	// retrieve full list of PermApplication
	public List<PermApplication> getAllPermApplications() {
		List<PermApplication> appList = permApplicationRepository.findAll();
		for (PermApplication permApplication : appList) {
			permApplication.setUser(usersService.getUserById(permApplication.getUserId()));
		}
		return appList;
	}

	
	// update PermApplication
	public PermApplication updatePermApplication(PermApplication permApplication) {
		getPermApplicationById(permApplication.getApplicationId());
		return permApplicationRepository.save(permApplication);
	}
	
	// end of PermApplication methods
	
	// write all applications to file
//	@Transactional
//	public boolean writeToFileAndUpload() {
//		try (BufferedWriter bw = new BufferedWriter(new FileWriter("submissions.json"))){
//			GsonBuilder gsonBuilder = new GsonBuilder();
//			gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
//			gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
//			Gson gson = gsonBuilder.setPrettyPrinting().create();
//			List<Application> appList = getAllApplications();
//			gson.toJson(appList, bw);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//		gateway.upload(new File("submissions.json"));
//		
//		applicationRepository.truncateApplicationTable();
//		return true;	
//	}
	
	
	// Read From JSON Related
//	public Iterable<Application> listFromJson(){
//		return applicationRepository.findAll();
//	}
//	
//	public Application save(Application application) {
//		return applicationRepository.save(application);
//	}
//	
//	public Iterable<Application> save(List<Application> applications) {
//		return applicationRepository.saveAll(applications);
//	}


}
