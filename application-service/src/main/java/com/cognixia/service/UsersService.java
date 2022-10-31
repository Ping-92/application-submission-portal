package com.cognixia.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cognixia.model.Users;

@Service
@FeignClient(name="USER-SERVICE")
public interface UsersService {
	@GetMapping("users/user/{id}")
	public Users getUserById(@PathVariable("id") int id );
	
	@GetMapping("users/current_user")
	public int getCurrentUserId();
}
