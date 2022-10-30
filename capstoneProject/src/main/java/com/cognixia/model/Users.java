package com.cognixia.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Users {
	
	@Id
	@GeneratedValue
	private int userId;
	@NotNull
	private String userName;
	@NotNull
	@Size(min=2, max=30)
	private String password;
	@NotNull
	private String email;
	@NotNull
	private String mobilePhone;
	private LocalDateTime lastLogin;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public LocalDateTime getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	public Users() {
		// TODO Auto-generated constructor stub
	}
	public Users(int userId, @NotNull String userName, @NotNull String password, @NotNull String email,
			@NotNull String mobilePhone, LocalDateTime lastLogin) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.mobilePhone = mobilePhone;
		this.lastLogin = lastLogin;
	}
	

}
