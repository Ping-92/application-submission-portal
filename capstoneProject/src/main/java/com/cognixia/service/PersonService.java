package com.cognixia.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.exception.PersonNotFoundException;
import com.cognixia.model.Person;
import com.cognixia.model.Users;
import com.cognixia.repository.PersonRepository;
import com.cognixia.repository.UsersRepository;

@Service
public class PersonService {
	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	UsersRepository usersRepository;
	
	public Person addPerson(Person person, Users user) {
		Person newPerson = personRepository.save(person);
		user.setUserId(newPerson.getPersonId());
		user.setLastLogin(LocalDateTime.now());
		usersRepository.save(user);
		return newPerson;
	}
	
	public Person getPersonById (int personId) {
		return personRepository.findById(personId).orElseThrow(PersonNotFoundException::new);
	}
	
	public Users getUsersByUsernameandPassword (Users user) {
		return usersRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
	}
}
