package com.cognixia.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cognixia.exception.PersonNotFoundException;

import com.cognixia.model.ErrorResponse;

@RestControllerAdvice
public class PersonControllerAdvice {

	@ExceptionHandler(PersonNotFoundException.class)
	public ResponseEntity<ErrorResponse> usersNotFoundException (PersonNotFoundException e){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("UserService-404", "User(s) not found!" ));
	}
}
