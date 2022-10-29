package com.cognixia.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Application {
	@Id
	@GeneratedValue
	private int applicationId;
	
	@NotNull
	private String name;
	
	@NotNull
	private String race;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate dateOfBirth;
	
	@NotNull
	private String countryOfBirth;
	
	@NotNull
	private String vaccinationStatus;
	
	private LocalDateTime submissionDateTime;
	private String applicationStatus;
	
	@Transient
	private Users user;
	
	public Application() {}

	public Application(int applicationId, String name, String race, LocalDate dateOfBirth,
			String countryOfBirth, String vaccinationStatus, LocalDateTime submissionDateTime, String applicationStatus,
			Users user) {
		super();
		this.applicationId = applicationId;
		this.name = name;
		this.race = race;
		this.dateOfBirth = dateOfBirth;
		this.countryOfBirth = countryOfBirth;
		this.vaccinationStatus = vaccinationStatus;
		this.submissionDateTime = submissionDateTime;
		this.applicationStatus = applicationStatus;
		this.user = user;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public int getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getCountryOfBirth() {
		return countryOfBirth;
	}

	public void setCountryOfBirth(String countryOfBirth) {
		this.countryOfBirth = countryOfBirth;
	}

	public String getVaccinationStatus() {
		return vaccinationStatus;
	}

	public void setVaccinationStatus(String vaccinationStatus) {
		this.vaccinationStatus = vaccinationStatus;
	}

	public LocalDateTime getSubmissionDateTime() {
		return submissionDateTime;
	}

	public void setSubmissionDateTime(LocalDateTime submissionDateTime) {
		this.submissionDateTime = submissionDateTime;
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
}
