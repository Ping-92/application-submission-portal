package com.cognixia.common;

import java.util.Date;
import java.util.List;

public class ErrorResponse {

	private Date timestamp = new Date();
	private String message;
	private String details;
	private List<org.springframework.validation.FieldError> fieldErrors;
	
	public List<org.springframework.validation.FieldError> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<org.springframework.validation.FieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	public ErrorResponse(String message, String details) {
		super();
		this.message = message;
		this.details = details;
	}
	
	public ErrorResponse(String message, String details,List<org.springframework.validation.FieldError> fieldErrors ) {
		super();
		this.message = message;
		this.details = details;
		this.fieldErrors = fieldErrors;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	
}
