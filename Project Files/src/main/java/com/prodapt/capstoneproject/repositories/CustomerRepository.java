package com.prodapt.capstoneproject.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.prodapt.capstoneproject.entities.Customer;
import jakarta.transaction.Transactional;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

	public Optional<Customer> findByUsername(String username);

	public Boolean existsByUsername(String username);

	@Transactional
	@Query(value = "SELECT c.name,c.email,c.phone,c.status,a.amount AS account_amount, COUNT(n.notificationid) AS dunning_notification_count FROM customer c JOIN account a ON c.id = a.customer_id LEFT JOIN dunning_notification n ON a.accountid = n.accountid GROUP BY c.name,c.email,c.phone,c.status,a.amount", nativeQuery = true)
	List<Object[]> getCustomerStatusReport();

	public Boolean existsByEmail(String email);
}