package com.prodapt.capstoneproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodapt.capstoneproject.entities.Payments;
import com.prodapt.capstoneproject.exceptions.PaymentNotFoundException;
import com.prodapt.capstoneproject.repositories.PaymentsRepository;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	PaymentsRepository payrep;

	@Override
	public Payments addPayments(Payments payments) {
		return payrep.save(payments);
	}

	@Override
	public Payments updatePayments(Payments payments)throws PaymentNotFoundException {
		if (getPayment(payments.getPaymentid()) == null) {
			throw new PaymentNotFoundException("Payment not found with id: " + payments.getPaymentid());
		}
		return payrep.save(payments);
	}

	@Override
	public Payments getPayment(Long id)throws PaymentNotFoundException {
		Optional<Payments> payment = payrep.findById(id);
		if (!payment.isPresent()) {
			throw new PaymentNotFoundException("Payment not found with id: " + id);
		}
		return payment.get();
	}

	@Override
	public void deletePayment(Long id) throws PaymentNotFoundException{
		if (getPayment(id) == null) {
			throw new PaymentNotFoundException("Payment not found with id: " + id);
		}
		payrep.deleteById(id);
	}

	@Override
	public List<Payments> getAllPayments() {
		return (List<Payments>) payrep.findAll();
	}

}