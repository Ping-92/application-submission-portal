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

import com.cognixia.model.Application;
import com.cognixia.service.ApplicationService;

@Controller
@RequestMapping("/application")
public class FormController implements WebMvcConfigurer{
	@Autowired
	ApplicationController applicationController;
	
	@Autowired
	ApplicationService applicationService;
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/results").setViewName("results");
	}
	
	@GetMapping("/applicationpage")
	public String showApplicationForm(Application appplication) {
		return "applicationForm";
	}
	
	@PostMapping("/applicationpage")
	public String performApplication(@Valid Application application, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "applicationForm";
		}
		applicationController.addApplication(application);
		return "redirect:/results";
	}
}
