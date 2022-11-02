package com.cognixia.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.stereotype.Service;

import com.cognixia.common.exception.ApplicationNotFoundException;
import com.cognixia.model.PermApplication;
import com.cognixia.repository.PermApplicationRepository;

@Service
public class PermApplicationService {

	@Autowired
	private PermApplicationRepository permApplicationRepository;

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Qualifier("writeApplicationDataIntoSqlDb")
	@Autowired
	Job writeApplicationDataIntoSqlDb;
	
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

		//batch job runner
	public boolean startJob(String jobName) {
		Map<String, JobParameter> params = new HashMap<>();
		
		// put current time in millseconds so current job will be unique everytime
		params.put("currentTime", new JobParameter(System.currentTimeMillis()));
		
		JobParameters jobParameters = new JobParameters(params);
		try {
			if (jobName.equals("writeApplicationDataIntoSqlDb")) {
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
}
