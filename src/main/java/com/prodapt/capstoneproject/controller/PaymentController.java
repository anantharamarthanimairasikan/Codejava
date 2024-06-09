package com.prodapt.capstoneproject.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prodapt.capstoneproject.entities.Payments;
import com.prodapt.capstoneproject.exceptions.PaymentNotFoundException;
import com.prodapt.capstoneproject.service.PaymentService;

import jakarta.transaction.Transactional;

@RestController
@Transactional
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
    PaymentService pservice;
	
	@PostMapping("/addpayment")
	    public ResponseEntity<Payments> addPayment(@RequestBody Payments payment) {
	        Payments newPayment = pservice.addPayments(payment);
	        return new ResponseEntity<>(newPayment, HttpStatus.CREATED);
	    }
	
	@GetMapping("/getallpayments")
	    public ResponseEntity<List<Payments>> getAllPayments() {
	        List<Payments> payments = pservice.getAllPayments();
	        return new ResponseEntity<>(payments, HttpStatus.OK);
	    }
	
	@GetMapping("/getpayment/{id}")
    public ResponseEntity<Payments> getPayment(@PathVariable Long id) throws PaymentNotFoundException {
        Payments payment = pservice.getPayment(id);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }
	
	@PutMapping("/updatepayment")
    public ResponseEntity<Payments> updatePayment(@RequestBody Payments payment) throws PaymentNotFoundException {
        Payments updatedPayment = pservice.updatePayments(payment);
        return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
    }
	
	@DeleteMapping("/deletepayment/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) throws PaymentNotFoundException {
        pservice.deletePayment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}