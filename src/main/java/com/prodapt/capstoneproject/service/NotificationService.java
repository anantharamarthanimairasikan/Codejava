package com.prodapt.capstoneproject.service;

import com.prodapt.capstoneproject.entities.Notification;
import com.prodapt.capstoneproject.exceptions.NotificationNotFoundException;
import java.util.List;

public interface NotificationService {

	Notification addNotification(Notification notification);

	Notification updateNotification(Notification notification)throws NotificationNotFoundException;

	Notification getNotification(Long id)throws NotificationNotFoundException;

	void deleteNotification(Long id)throws NotificationNotFoundException;

	List<Notification> getAllNotifications();
}