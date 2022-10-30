package com.cognixia.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cognixia.controller.PersonController;

import com.cognixia.model.Person;
import com.cognixia.model.Users;
import com.cognixia.service.PersonService;

@Controller
public class UserformController implements WebMvcConfigurer {

	@Autowired
	PersonController personController;

	@Autowired
	PersonService personService;

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/results").setViewName("results");
	}

	@GetMapping("/register")
	public String showRegistrationForm(Person personForm, Users user) {
		return "registerForm";
	}

	@PostMapping("/register")
	public String checkPersonRegistration(@Valid Person person, BindingResult bindingResult, Users user) {

		if (bindingResult.hasErrors()) {
			return "registerForm";
		}
		personController.addPerson(person, user);
		return "redirect:/results";
	}

	@GetMapping("/login")
	public String showLoginForm(Users user) {
		return "login";
	}

	@PostMapping("/login")
	public String verifyLogin(Users user) {
		Users verifyUser = personService.getUsersByUsernameandPassword(user);
		if (verifyUser != null) {
			return "loginSuccess";
		} else {
			return "loginFail";
		}
	}

}