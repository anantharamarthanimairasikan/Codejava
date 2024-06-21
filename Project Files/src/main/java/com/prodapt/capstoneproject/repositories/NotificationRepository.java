package com.prodapt.capstoneproject.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.prodapt.capstoneproject.entities.Notification;

import jakarta.transaction.Transactional;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {

	@Query(value = "SELECT n.send_Date AS dunning_date,n.notificationid AS notification_id, a.customer_id AS customer_id, n.message AS dunning_message, p.payment_Date AS response_date, p.amount AS response_amount, CASE WHEN p.payment_Date IS NOT NULL THEN 'Successful' ELSE 'Unsuccessful' END AS outcome FROM Dunning_Notification n JOIN account a ON n.accountid = a.accountid LEFT JOIN payment p ON n.accountid = p.accountid AND p.payment_Date >= n.send_Date ORDER BY n.send_Date DESC;", nativeQuery = true)
	List<Object[]> findDunningNotifications();
	
	@Transactional
	@Query(value = "WITH recovery_rate AS (SELECT SUM(p.amount) / SUM(a.amount) AS recovery_rate FROM  payment p JOIN  account a ON p.accountid = a.accountid WHERE p.payment_Date >= DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY)), dunning_effectiveness AS ( SELECT COUNT(DISTINCT n.notificationid) / COUNT(DISTINCT a.accountid) AS dunning_effectiveness FROM dunning_notification n JOIN account a ON n.accountid = a.accountid WHERE n.send_Date >= DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY)), customer_response_times AS (SELECT AVG(DATEDIFF(p.payment_Date, n.send_Date)) AS avg_response_time FROM payment p JOIN dunning_notification n ON p.accountid = n.accountid WHERE p.payment_Date >= DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY) AND n.send_Date >= DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY)) SELECT r.recovery_rate, d.dunning_effectiveness, c.avg_response_time FROM recovery_rate r, dunning_effectiveness d, customer_response_times c", nativeQuery = true)
	List<Object[]> findPerformanceDashboardReport();

}
