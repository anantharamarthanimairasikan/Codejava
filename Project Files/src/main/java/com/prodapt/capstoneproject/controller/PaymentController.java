package com.prodapt.capstoneproject.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/api/payment")
@CrossOrigin(origins = "*", maxAge = 3600)
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
    public ResponseEntity<?> getPayment(@PathVariable Long id) {
        try {
            Payments payment = pservice.getPayment(id);
            return new ResponseEntity<>(payment, HttpStatus.OK);
        } catch (PaymentNotFoundException e) {
            return new ResponseEntity<>("Payment not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updatepayment")
    public ResponseEntity<?> updatePayment(@RequestBody Payments payment) {
        try {
            Payments updatedPayment = pservice.updatePayments(payment);
            return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
        } catch (PaymentNotFoundException e) {
            return new ResponseEntity<>("Payment not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deletepayment/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable Long id) {
        try {
            pservice.deletePayment(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (PaymentNotFoundException e) {
            return new ResponseEntity<>("Payment not found", HttpStatus.NOT_FOUND);
        }
    }
}