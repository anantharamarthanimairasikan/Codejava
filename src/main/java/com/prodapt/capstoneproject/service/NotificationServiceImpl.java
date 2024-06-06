package com.prodapt.capstoneproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodapt.capstoneproject.entities.Notification;
import com.prodapt.capstoneproject.exceptions.NotificationNotFoundException;
import com.prodapt.capstoneproject.repositories.NotificationRepository;

@Service
public class NotificationServiceImpl implements NotificationService {
	
	@Autowired
	NotificationRepository repo;

	@Override
	public Notification addNotification(Notification notification) {
		return repo.save(notification);
	}

	@Override
	public Notification updateNotification(Notification notification)throws NotificationNotFoundException {
		if (getNotification(notification.getNotificationid()) == null) {
			throw new NotificationNotFoundException("Notification not found with id: " + notification.getNotificationid());
		}
		return repo.save(notification);
	}

	@Override
	public Notification getNotification(Long id) throws NotificationNotFoundException{
		Optional<Notification> notification = repo.findById(id);
		if (!notification.isPresent()) {
			throw new NotificationNotFoundException("Notification not found with id: " + id);
		}
		return notification.get();
	}

	@Override
	public void deleteNotification(Long id)throws NotificationNotFoundException {
		if (getNotification(id) == null) {
			throw new NotificationNotFoundException("Notification not found with id: " + id);
		}
		repo.deleteById(id);
	}

	@Override
	public List<Notification> getAllNotifications() {
		return (List<Notification>) repo.findAll();
	}

}