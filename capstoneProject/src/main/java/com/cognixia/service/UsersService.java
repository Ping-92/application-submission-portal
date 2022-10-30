package com.cognixia.service;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.exception.UsersNotFoundException;
import com.cognixia.model.Users;
import com.cognixia.repository.UserRepository;

@Service
public class UsersService {
	@Autowired
	private UserRepository userRepository;

	public List<Users> getAllUser() {
		return userRepository.findAll();
	}

	public Users addUser(Users user) {
		user.setLastLogin(LocalDateTime.now());
		return userRepository.save(user);

	}

	public Users getUserById(int id) {
		return userRepository.findById(id).orElseThrow(UsersNotFoundException::new);
	}
}
