package com.prodapt.capstoneproject.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodapt.capstoneproject.entities.EMessage;
import com.prodapt.capstoneproject.entities.EResponse;
import com.prodapt.capstoneproject.entities.Notification;
import com.prodapt.capstoneproject.exceptions.NotificationNotFoundException;
import com.prodapt.capstoneproject.model.DunningReport;
import com.prodapt.capstoneproject.model.ExceptionReport;
import com.prodapt.capstoneproject.model.PerformanceDashboardReport;
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
		if (getNotification(notification.getNotificationid()) != null) {
			return repo.save(notification);
		}else {
		throw new NotificationNotFoundException("Notification was not found with id: " + notification.getNotificationid());
	}
	}

	@Override
	public Notification getNotification(Long id) throws NotificationNotFoundException{
		Optional<Notification> notification = repo.findById(id);
		if (notification.isPresent()) {
			return notification.get();
		}else {
		throw new NotificationNotFoundException("Notification not found with id: " + id);
		}
		
	}

	@Override
	public void deleteNotification(Long id)throws NotificationNotFoundException {
		if (getNotification(id) != null) {
			repo.deleteById(id);
		}else {
		throw new NotificationNotFoundException("Notification not found with id: " + id);
		}
	}

	@Override
	public List<Notification> getAllNotifications() {
		return (List<Notification>) repo.findAll();
	}

	@Override
	public List<DunningReport> getDunningReport() {
		 List<Object[]> results = repo.findPerformanceDashboardReport();
	        List<DunningReport> reports = new ArrayList<>();
	        for (Object[] result : results) {
	        	DunningReport report = new DunningReport();
	        	report.setNotificationid((Long) result[0]);
	            report.setAccount_id((Long) result[1]);
	            report.setSendDate((LocalDate) result[2]);
	            report.setMethod((EMessage) result[3]);
	            report.setResponse((EResponse) result[4]);
	            reports.add(report);

	        }
		return reports;
	}

	@Override
	public List<PerformanceDashboardReport> getPerformanceDashboardReport() {
		 List<Object[]> results = repo.findPerformanceDashboardReport();
	        List<PerformanceDashboardReport> reports = new ArrayList<>();
	        for (Object[] result : results) {
	        	PerformanceDashboardReport report = new PerformanceDashboardReport();
	        	report.setRecoveryRate((Integer) result[0]);;
	            report.setEffectiveness((Integer) result[1]);
	            report.setAvgResponseTime((Double) result[2]);
	            report.setTotal_notifications((Integer) result[3]);
	            report.setTotal_accounts((Integer) result[4]);
	            report.setIgnored_responses((Integer) result[5]);
	            report.setUndeliverable_responses((Integer) result[6]);
	            reports.add(report);
	        }
		return reports;
	}
	
	@Override
	public List<ExceptionReport> getExceptionReport() {
		 List<Object[]> results = repo.findPerformanceDashboardReport();
	        List<ExceptionReport> reports = new ArrayList<>();
	        for (Object[] result : results) {
	        	ExceptionReport report = new ExceptionReport();
	        	report.setAccountId((Long) result[0]);
	            report.setFailed_payments((Integer) result[1]);
	            reports.add(report);
	        }
		return reports;
	}

}