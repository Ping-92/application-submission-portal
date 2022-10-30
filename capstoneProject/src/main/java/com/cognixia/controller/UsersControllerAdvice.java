package com.cognixia.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cognixia.exception.UsersNotFoundException;
import com.cognixia.model.ErrorResponse;

@RestControllerAdvice
public class UsersControllerAdvice {

	@ExceptionHandler(UsersNotFoundException.class)
	public ResponseEntity<ErrorResponse> usersNotFoundException (UsersNotFoundException e){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("UserService-404", "User(s) not found!" ));
	}
}
