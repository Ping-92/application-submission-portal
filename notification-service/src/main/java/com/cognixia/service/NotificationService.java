package com.cognixia.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognixia.model.Notification;
import com.cognixia.model.PermApplication;
import com.cognixia.repository.NotificationRepository;

@Service
public class NotificationService {
	
	@Autowired
	NotificationRepository notificationRepository;
	
	@Autowired
	ApplicationService applicationService;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String sender;
	

	// To send notification email
	@Transactional
	@Scheduled(cron = "0 21 15 * * ?")
	public String sendMail() {

		List<PermApplication> retrieveAllAppliactionsList = applicationService.getAllPermApplications();
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
					Notification newNotif = addNotification(a);
					mailMessage.setFrom(sender);
					mailMessage.setTo(a.getUser().getEmail());
					mailMessage.setText(newNotif.getMessage());
					mailMessage.setSubject("Application Outcome");

					// Sending the mail
					javaMailSender.send(mailMessage);
					a.setApplicationStatus("Processed-EmailSent");
					applicationService.updatePermApplication(a.getApplicationId(), a);
					addNotification(a);
				}

				// Catch block to handle the exceptions
				catch (Exception e) {
					a.setApplicationStatus("Processed-EmailError");
					applicationService.updatePermApplication(a.getApplicationId(), a);
					System.out.println(e.getMessage());
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
		newNotification.setMessage("Hello, " + permApplication.getName() +" your application has been successfully processed!");
		newNotification.setNotificationSent(LocalDateTime.now());
		newNotification.setReceipientEmail(permApplication.getUser().getEmail());
		notificationRepository.save(newNotification);
		return newNotification;
	}

	public List<Notification> getMail() {
		return notificationRepository.findAll();
	}

}