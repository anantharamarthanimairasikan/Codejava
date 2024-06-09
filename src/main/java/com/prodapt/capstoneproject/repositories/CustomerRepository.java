package com.prodapt.capstoneproject.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.prodapt.capstoneproject.entities.Customer;
import com.prodapt.capstoneproject.entities.UserEntity;
import jakarta.transaction.Transactional;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

	public Optional<UserEntity> findByUsername(String username);

	public Boolean existsByUsername(String username);

	@Transactional
	@Query(value = "SELECT c.name, c.email, c.phone, c.status, COALESCE(SUM(p.amount), 0), COUNT(n.notificationid), GROUP_CONCAT(DISTINCT CONCAT(n.method, ': ', n.send_Date))FROM customer c JOIN account a ON c.id = a.customer_id LEFT JOIN payment p ON a.accountid = p.accountid AND p.payment_Date < CURRENT_DATE AND p.amount > 0 LEFT JOIN Dunning_Notification n ON a.accountid = n.accountid GROUP BY c.name, c.email, c.phone, c.status", nativeQuery = true)
	List<Object[]> getCustomerStatusReport();

	public Boolean existsByEmail(String email);
}