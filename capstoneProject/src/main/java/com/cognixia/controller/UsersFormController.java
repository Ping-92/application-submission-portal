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

import com.cognixia.model.Users;
import com.cognixia.service.UsersService;


@Controller
@RequestMapping("/users")
public class UsersFormController implements WebMvcConfigurer{
	@Autowired
	UsersController usersController;
	
	@Autowired
	UsersService userService;


	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/results").setViewName("results");
	}
	
	@GetMapping("/userpage")
	public String showUserForm(Users user) {
		return "userForm";
	}
	
	@PostMapping("/userpage")
	public String performApplication(@Valid Users user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "userForm";
		}
		usersController.addUser(user);
		return "redirect:/results";
	}
}