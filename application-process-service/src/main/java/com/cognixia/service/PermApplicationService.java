package com.cognixia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
}
