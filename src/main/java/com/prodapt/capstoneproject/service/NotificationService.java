package com.prodapt.capstoneproject.service;

import com.prodapt.capstoneproject.entities.Notification;
import com.prodapt.capstoneproject.exceptions.NotificationNotFoundException;
import com.prodapt.capstoneproject.model.DunningReport;
import com.prodapt.capstoneproject.model.ExceptionReport;
import com.prodapt.capstoneproject.model.PerformanceDashboardReport;

import java.util.List;

public interface NotificationService {

	Notification addNotification(Notification notification);

	Notification updateNotification(Notification notification)throws NotificationNotFoundException;

	Notification getNotification(Long id)throws NotificationNotFoundException;

	void deleteNotification(Long id)throws NotificationNotFoundException;

	List<Notification> getAllNotifications();
	
	List<DunningReport> getDunningReport();
	
	List<PerformanceDashboardReport> getPerformanceDashboardReport();
	
	List<ExceptionReport> getExceptionReport();
}