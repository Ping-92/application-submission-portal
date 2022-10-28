package com.cognixia.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Application {
	@Id
	@GeneratedValue
	private int applicationId;
	private String name;
	private String race;
	private int age;
	private LocalDate dateofBirth;
	private String countryofBirth;
	private String vaccinationStatus;
	private LocalDateTime submissionDateTime;
	private String applicationStatus;
	
	@Transient
	private Users user;

	public Application(int applicationId, String name, String race, int age, LocalDate dateofBirth,
			String countryofBirth, String vaccinationStatus, LocalDateTime submissionDateTime, String applicationStatus,
			Users user) {
		super();
		this.applicationId = applicationId;
		this.name = name;
		this.race = race;
		this.age = age;
		this.dateofBirth = dateofBirth;
		this.countryofBirth = countryofBirth;
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public LocalDate getDateofBirth() {
		return dateofBirth;
	}

	public void setDateofBirth(LocalDate dateofBirth) {
		this.dateofBirth = dateofBirth;
	}

	public String getCountryofBirth() {
		return countryofBirth;
	}

	public void setCountryofBirth(String countryofBirth) {
		this.countryofBirth = countryofBirth;
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
