package com.cognixia.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.cognixia.model.Users;

@Service
@FeignClient(name="USER-SERVICE")
public interface UsersService {
	public ResponseEntity<Users> getUserById(@PathVariable("id") int id );
}
