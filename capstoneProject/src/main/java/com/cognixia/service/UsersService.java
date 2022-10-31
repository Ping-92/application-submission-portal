package com.cognixia.service;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.cognixia.exception.UsersNotFoundException;
import com.cognixia.model.Users;
import com.cognixia.repository.UserRepository;

@Service
public class UsersService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
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

	
	public Users validateUserandPassword(Users user) {
		return userRepository.findByUserNameAndPassword(user.getUserName(), user.getPassword());
		
	}
<<<<<<< HEAD
	
	public boolean insertCurrentUser(Users user) {
		jdbcTemplate.update("DELETE FROM login_user");
		jdbcTemplate.update("INSERT INTO login_user VALUES (?)", user.getUserId());
		return true;
	}
=======

>>>>>>> refs/remotes/origin/users
}
