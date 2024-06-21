package com.prodapt.capstoneproject.service;

import java.util.List;

import com.prodapt.capstoneproject.entities.Notification;
import com.prodapt.capstoneproject.exceptions.NotificationNotFoundException;
import com.prodapt.capstoneproject.model.DunningReport;
import com.prodapt.capstoneproject.model.PerformanceDashboardReport;

public interface NotificationService {

	Notification addNotification(Notification notification);

	Notification updateNotification(Notification notification)throws NotificationNotFoundException;

	Notification getNotification(Long id)throws NotificationNotFoundException;

	void deleteNotification(Long id)throws NotificationNotFoundException;

	List<Notification> getAllNotifications();
	
	List<DunningReport> getDunningReport();
	
	List<PerformanceDashboardReport> getPerformanceDashboardReport();
	
}