package com.prodapt.capstoneproject.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.prodapt.capstoneproject.entities.Notification;
@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {
	
	@Query(value = "SELECT n.notificationid, a.accountid, n.send_date, n.method, n.response FROM Dunning_Notification n JOIN Account a ON n.accountid = a.accountid ORDER BY n.send_date DESC", nativeQuery = true)
	List<Object[]> findDunningNotifications();
	
	@Query(value="SELECT SUM(CASE WHEN n.response = 'PAID' THEN 1 ELSE 0 END) / COUNT(*) AS recovery_rate, SUM(CASE WHEN n.response = 'CONTACTED' THEN 1 ELSE 0 END) / COUNT(*) AS dunning_effectiveness, AVG(DATEDIFF(p.payment_date, n.send_date)) AS avg_response_time, COUNT(*) AS total_notifications, COUNT(DISTINCT a.accountid) AS total_accounts, COUNT(CASE WHEN n.response = 'IGNORED' THEN 1 ELSE 0 END) AS ignored_responses, COUNT(CASE WHEN n.response = 'UNDELIVERABLE' THEN 1 ELSE 0 END) AS undeliverable_responses FROM dunning_notification n JOIN account a ON n.accountid = a.accountid JOIN payment p ON n.accountid = p.accountid WHERE  n.send_date >= DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY)GROUP BY  a.accountid",nativeQuery = true)
	List<Object[]>findPerformanceDashboardReport();
	
	@Query(value="SELECT n.accountid, COUNT(*) as consecutive_failed_payments FROM Dunning_Notification n WHERE n.response IN ('IGNORED', 'UNDELIVERABLE') GROUP BY n.accountid HAVING COUNT(*) > 1 ORDER BY consecutive_failed_payments DESC",nativeQuery = true)
	List<Object[]>findExceptionsReport();

}

