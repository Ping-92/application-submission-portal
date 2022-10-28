package com.cognixia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.cognixia.model.Notification;
import com.cognixia.repository.NotificationRepository;

@Service
public class NotificationService {
	
	@Autowired
	NotificationRepository notificationRepository;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String sender;

	// To send a simple email
	public String sendSimpleMail(Notification notification) {

		// Try block to check for exceptions
		try {

			// Creating a simple mail message
			SimpleMailMessage mailMessage = new SimpleMailMessage();

			// Setting up necessary details
			mailMessage.setFrom(sender);
			mailMessage.setTo(notification.getReceipientEmail());
			mailMessage.setText(notification.getMessage());
			mailMessage.setSubject("Testing");

			// Sending the mail
			javaMailSender.send(mailMessage);
			return "Mail Sent Successfully...";
		}

		// Catch block to handle the exceptions
		catch (Exception e) {
			System.out.println(e.getMessage());
			return "Error while Sending Mail";
		}
	}
	
	public Notification addNotification(Notification notification) {
		return notificationRepository.save(notification);
	}
}
