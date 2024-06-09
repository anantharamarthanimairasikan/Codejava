package com.prodapt.capstoneproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prodapt.capstoneproject.entities.Notification;
import com.prodapt.capstoneproject.exceptions.NotificationNotFoundException;
import com.prodapt.capstoneproject.service.NotificationService;

import jakarta.transaction.Transactional;

@RestController
@Transactional
@RequestMapping("/notification")
public class NotificationController {
	
	@Autowired
    private NotificationService nservice;
	
	@PostMapping("/addnotification")
    public ResponseEntity<Notification> addNotification(@RequestBody Notification notification) {
        Notification newNotification = nservice.addNotification(notification);
        return new ResponseEntity<>(newNotification, HttpStatus.CREATED);
    }
	
	@GetMapping("/getallnotifications")
	public ResponseEntity<List<Notification>> getAllNotifications() {
	        List<Notification> notifications = nservice.getAllNotifications();
	        return new ResponseEntity<>(notifications, HttpStatus.OK);
	 }
	
	@GetMapping("/getnotification/{id}")
    public ResponseEntity<Notification> getNotification(@PathVariable Long id) throws NotificationNotFoundException {
        Notification notification = nservice.getNotification(id);
        return new ResponseEntity<>(notification, HttpStatus.OK);
    }
	
	@PutMapping("/updatenotification")
    public ResponseEntity<Notification> updateNotification(@RequestBody Notification notification) throws NotificationNotFoundException {
        Notification updatedNotification = nservice.updateNotification(notification);
        return new ResponseEntity<>(updatedNotification, HttpStatus.OK);
    }
	
	@DeleteMapping("/deletenotification/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) throws NotificationNotFoundException {
        nservice.deleteNotification(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}