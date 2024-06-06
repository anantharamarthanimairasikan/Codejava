package com.prodapt.capstoneproject.service;

import com.prodapt.capstoneproject.entities.Payments;
import com.prodapt.capstoneproject.exceptions.PaymentNotFoundException;

import java.util.List;

public interface PaymentService {

	Payments addPayments(Payments payments);

	Payments updatePayments(Payments payments)throws PaymentNotFoundException;

	Payments getPayment(Long id)throws PaymentNotFoundException;

	void deletePayment(Long id)throws PaymentNotFoundException;

	List<Payments> getAllPayments();
}