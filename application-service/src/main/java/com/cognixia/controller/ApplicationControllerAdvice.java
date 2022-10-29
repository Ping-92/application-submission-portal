package com.cognixia.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cognixia.common.ErrorResponse;
import com.cognixia.common.exception.ApplicationNotFoundException;

@RestControllerAdvice
public class ApplicationControllerAdvice {
	
	@ExceptionHandler(ApplicationNotFoundException.class)
	public ResponseEntity<ErrorResponse> applicationNotFoundException (ApplicationNotFoundException e){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("ApplicationService-404", "Application(s) not found!" ));
	}
	
}
