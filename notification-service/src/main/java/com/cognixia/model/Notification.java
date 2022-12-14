package com.cognixia.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Notification {

	@Id
	@GeneratedValue
	private int notificationId;
	private String message;
	private String receipientEmail;
	private LocalDateTime notificationSent;

	private int applicationId;
	
	@Transient
	private PermApplication application;

	public Notification() {

	}
	
	public Notification(int notificationId, String message, String receipientEmail, LocalDateTime notificationSent,
			int applicationId, PermApplication application) {
		super();
		this.notificationId = notificationId;
		this.message = message;
		this.receipientEmail = receipientEmail;
		this.notificationSent = notificationSent;
		this.applicationId = applicationId;
		this.application = application;
	}

	public int getApplicationId() {
		return applicationId;
	}



	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}



	public int getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getReceipientEmail() {
		return receipientEmail;
	}

	public void setReceipientEmail(String receipientEmail) {
		this.receipientEmail = receipientEmail;
	}

	public LocalDateTime getNotificationSent() {
		return notificationSent;
	}

	public void setNotificationSent(LocalDateTime notificationSent) {
		this.notificationSent = notificationSent;
	}

	public PermApplication getApplication() {
		return application;
	}

	public void setApplication(PermApplication application) {
		this.application = application;
	}

}
