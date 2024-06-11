package com.prodapt.capstoneproject.service;

import java.math.BigDecimal;
import java.sql.Date;
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
		Optional<Notification> notifications = repo.findById(notification.getNotificationid());
		if (notifications.isPresent()) {
			return repo.save(notification);
		}else {
		throw new NotificationNotFoundException("Notification was not found with id: " + notification.getNotificationid());
	}
	}

	@Override
	public Notification getNotification(Long id) throws NotificationNotFoundException {
	    Optional<Notification> notification = repo.findById(id);
	    if (notification.isPresent()) {
	        return notification.get();
	    } else {
	        throw new NotificationNotFoundException("Notification not found with id: " + id);
	    }
	}

	 @Override
	    public void deleteNotification(Long id) throws NotificationNotFoundException {
	        if (repo.existsById(id)) {
	            repo.deleteById(id);
	        } else {
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
	        Date sqlDate = (Date) result[2];
	        report.setSendDate(sqlDate.toLocalDate());
	        report.setMethod(Enum.valueOf(EMessage.class, (String) result[3]));
	        report.setResponse(Enum.valueOf(EResponse.class, (String) result[4]));
	        reports.add(report); // Add to list
	    }
	    return reports;
	}

	@Override
	public List<PerformanceDashboardReport> getPerformanceDashboardReport() {
		 List<Object[]> results = repo.findPerformanceDashboardReport();
	        List<PerformanceDashboardReport> reports = new ArrayList<>();
	        for (Object[] result : results) {
	        	PerformanceDashboardReport report = new PerformanceDashboardReport();
	        	report.setRecoveryRate((BigDecimal) result[0]);;
	            report.setEffectiveness((BigDecimal) result[1]);
	            report.setAvgResponseTime((BigDecimal) result[2]);
	            report.setTotal_notifications((Long) result[3]);
	            report.setTotal_accounts((Long) result[4]);
	            report.setIgnored_responses((Long) result[5]);
	            report.setUndeliverable_responses((Long) result[6]);
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
	            report.setFailed_payments((Long) result[1]);
	            reports.add(report);
	        }
		return reports;
	}

}