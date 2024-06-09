package com.prodapt.capstoneproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodapt.capstoneproject.entities.Epaymethod;
import com.prodapt.capstoneproject.entities.Payments;
import com.prodapt.capstoneproject.exceptions.PaymentNotFoundException;
import com.prodapt.capstoneproject.model.PaymentDetails;
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
		if (getPayment(payments.getPaymentid()) != null) {
			return payrep.save(payments);
		}else {
		throw new PaymentNotFoundException("Payment details was not found with id: " + payments.getPaymentid());
		}
	}

	@Override
	public Payments getPayment(Long id)throws PaymentNotFoundException {
		Optional<Payments> payment = payrep.findById(id);
		if (payment.isPresent()) {
			return payment.get();
		}else {
			throw new PaymentNotFoundException("Payment not found with id: " + id);
		}
		
	}

	@Override
	public void deletePayment(Long id) throws PaymentNotFoundException{
		if (getPayment(id) != null) {
			payrep.deleteById(id);
		}else {
		throw new PaymentNotFoundException("Payment not found with id: " + id);
		}
	}

	@Override
	public List<Payments> getAllPayments() {
		return (List<Payments>) payrep.findAll();
	}

	@Override
	public List<PaymentDetails> getPaymentDetailsReport() {
		List<Object[]> results = payrep.findPaymentDetails();
        List<PaymentDetails> reports = new ArrayList<>();
        for (Object[] result : results) {
        	PaymentDetails report = new PaymentDetails();
            report.setPayment_id((Long) result[0]);
            report.setCustomer_name((String) result[1]);
            report.setEmail((String) result[2]);
            report.setPhone((Long) result[3]);
            java.sql.Date sqlDate = (java.sql.Date) result[4];
            report.setPayment_date(sqlDate.toLocalDate());
            report.setAmount((Integer) result[5]);
            report.setMethod(Enum.valueOf(Epaymethod.class,(String)result[6]));
            reports.add(report);
        }
        return reports;
	}

}