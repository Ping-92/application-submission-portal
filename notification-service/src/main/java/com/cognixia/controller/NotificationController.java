package com.cognixia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.model.Notification;
import com.cognixia.service.NotificationService;

@RestController
public class NotificationController {
	@Autowired
	private NotificationService notificationService;

	// Sending a simple Email
	@PostMapping("/send")
	public String sendMail() {
		String status = notificationService.sendMail();
		return status;
	}
	
	@GetMapping("/")
	public List<Notification> getMail() {
		return notificationService.getMail();
	}
}
