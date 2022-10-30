package com.cognixia.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cognixia.model.Users;
import com.cognixia.service.UsersService;



@RestController
@RequestMapping("/users")
public class UsersController {
	@Autowired
	UsersService usersService;
	
	@GetMapping
	public ResponseEntity<List<Users>> getAllUser(){
		return ResponseEntity.ok(usersService.getAllUser());
	}
	@PostMapping("/user")
	public ResponseEntity<Users> addUser(@RequestBody @Valid Users user){
		Users newUser = usersService.addUser(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newUser.getUserId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<Users> getUserById(@PathVariable("id") int id ){
		Users user = usersService.getUserById(id);
		if (user == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(user);
		}
	}
	
}
