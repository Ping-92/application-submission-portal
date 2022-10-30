package com.cognixia.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Users{
	@Id
	private int usersId;
	
	private String password;
	
	private String username;
	private LocalDateTime lastLogin;
	public Users() {
	}

	public int getUsersId() {
		return usersId;
	}

	public void setUserId(int usersId) {
		this.usersId = usersId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public void setUsersId(int usersId) {
		this.usersId = usersId;
	}
	
	
	
}
