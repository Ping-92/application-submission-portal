package com.cognixia.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Notification {

	@Id
	@GeneratedValue
	private int notificationId;
	private String message;
	private String receipientEmail;
	private LocalDateTime notificationSent;

	private Application application;

	public Notification() {

	}

	public Notification(int notificationId, String message, String receipientEmail, LocalDateTime notificationSent,
			Application application) {
		super();
		this.notificationId = notificationId;
		this.message = message;
		this.receipientEmail = receipientEmail;
		this.notificationSent = notificationSent;
		this.application = application;
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

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

}
