package com.cognixia.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.common.exception.ApplicationIDMismatchException;
import com.cognixia.model.PermApplication;
import com.cognixia.service.PermApplicationService;

@RestController
@RequestMapping("/permapplication")
public class PermApplicationController {
	
	@Autowired
	private PermApplicationService permApplicationService;
	
		// get all permApplication
		@GetMapping("/")
		public ResponseEntity<List<PermApplication>> getAllPermApplications(){
			return ResponseEntity.ok(permApplicationService.getAllPermApplications());
		}
		
		// get by permApplication Id
		@GetMapping("/{applicationid}")
		public ResponseEntity<PermApplication> getPermApplicationById(@PathVariable("applicationid") int applicationId){
			PermApplication permApplication = permApplicationService.getPermApplicationById(applicationId);
			if (permApplication == null) {
				return ResponseEntity.notFound().build();
			} else {
				return ResponseEntity.ok(permApplication);
			}
		}
		
		// update permApplication
		@PutMapping("/{applicationid}")
		public ResponseEntity<PermApplication> updatePermApplication(@PathVariable int applicationid, @Valid @RequestBody PermApplication permApplication) {
			if (applicationid != permApplication.getApplicationId()) {
				throw new ApplicationIDMismatchException("IDs do not match!");
			}
			PermApplication updatedPermApplication = permApplicationService.updatePermApplication(permApplication);
			if (updatedPermApplication == null) {
				return ResponseEntity.notFound().build();
			} else {
				return ResponseEntity.ok(updatedPermApplication);
			}
		}

}
