package com.cognixia.controller;

import java.net.URI;

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

import com.cognixia.model.Person;
import com.cognixia.model.Users;
import com.cognixia.service.PersonService;

@RestController
@RequestMapping("/persondb")
public class PersonController {
	@Autowired
	PersonService personService;
	
	@PostMapping("/person")
	public ResponseEntity<Person> addPerson(@RequestBody @Valid Person person, Users user){
		Person newPerson = personService.addPerson(person, user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newPerson.getPersonId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Person> getPersonById(@PathVariable int id){
		Person person = personService.getPersonById(id);
		if (person == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(person);
		}
	}
	
	@GetMapping("users/")
	public ResponseEntity<Users> getUsersByUsernameandPassword(@RequestBody Users user){
		Users findUser = personService.getUsersByUsernameandPassword(user);
		if (user == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(findUser); 
		}
	}

}
