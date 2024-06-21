package com.prodapt.capstoneproject.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.prodapt.capstoneproject.entities.Payments;
@Repository
public interface PaymentsRepository extends CrudRepository<Payments, Long> {
	
	@Query(value = "SELECT p.paymentid, c.name, c.email, c.phone, p.payment_Date, p.amount, p.method FROM payment p JOIN account a ON p.accountid = a.accountid JOIN customer c ON a.customer_id = c.id ORDER BY p.payment_Date DESC", nativeQuery = true)
	List<Object[]> findPaymentDetails();

}
