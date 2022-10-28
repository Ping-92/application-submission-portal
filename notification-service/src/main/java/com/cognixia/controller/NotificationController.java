package com.cognixia.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.model.Notification;
import com.cognixia.service.NotificationService;

@RestController
public class NotificationController {
    @Autowired private NotificationService notificationService;
    
    // Sending a simple Email
    @PostMapping("/send")
    public String
    sendMail(@RequestBody Notification details)
    {
    	Notification updateTimeNotification = details;
        String status = notificationService.sendSimpleMail(updateTimeNotification);
        updateTimeNotification.setNotificationSent(LocalDateTime.now());
        notificationService.addNotification(updateTimeNotification);
        return status;
    }
}
