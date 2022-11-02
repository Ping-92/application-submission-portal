package com.cognixia.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.cognixia.model.Notification;
import com.cognixia.model.PermApplication;
import com.cognixia.repository.NotificationRepository;

@Service
public class NotificationService {
	
	@Autowired
	NotificationRepository notificationRepository;
	
	PermApplicationService permApplicationService;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String sender;

	// To send a simple email
	public String sendMail() {

		List<PermApplication> retrieveAllAppliactionsList = permApplicationService.getAllPermApplications();
		List<PermApplication> listToSendEmail = new ArrayList<PermApplication>();

		for (PermApplication a : retrieveAllAppliactionsList) {
			if (a.getApplicationStatus().equals("Processed-EmailNotSent")) {
				listToSendEmail.add(a);
			}
		}

		if (!listToSendEmail.isEmpty()) {
			for (PermApplication a : listToSendEmail) {
				// Try block to check for exceptions
				try {
					// Creating a simple mail message
					SimpleMailMessage mailMessage = new SimpleMailMessage();

					// Setting up necessary details
					mailMessage.setFrom(sender);
					mailMessage.setTo(a.getUser().getEmail());
					mailMessage.setText("Application has been successfully processed!");
					mailMessage.setSubject("Testing");

					// Sending the mail
					javaMailSender.send(mailMessage);
					a.setApplicationStatus("Processed-EmailSent");
					permApplicationService.updatePermApplication(a);
					addNotification(a);
					return "Mail Sent Successfully...";
				}

				// Catch block to handle the exceptions
				catch (Exception e) {
					a.setApplicationStatus("Processed-EmailError");
					permApplicationService.updatePermApplication(a);
					System.out.println(e.getMessage());
					return "Error while Sending Mail";
				}
			}
		} else {
			return "No Email Notifications to send out!";
		}
		return "Done";
	}
	
	public Notification addNotification(PermApplication permApplication) {
		Notification newNotification = new Notification();
		newNotification.setApplication(permApplication);
		newNotification.setApplicationId(permApplication.getApplicationId());
		newNotification.setMessage("Application has been successfully processed!");
		newNotification.setNotificationSent(LocalDateTime.now());
		newNotification.setReceipientEmail(permApplication.getUser().getEmail());
		return newNotification;
	}
}