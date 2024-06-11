package com.prodapt.capstoneproject.controllertests;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.prodapt.capstoneproject.controller.PaymentController;
import com.prodapt.capstoneproject.entities.Account;
import com.prodapt.capstoneproject.entities.Epaymethod;
import com.prodapt.capstoneproject.entities.Payments;
import com.prodapt.capstoneproject.exceptions.PaymentNotFoundException;
import com.prodapt.capstoneproject.service.PaymentService;

public class PaymentControllerTest {
	@InjectMocks
    private PaymentController paymentController;
 
    @Mock
    private PaymentService paymentService;
 
    private Payments payment;
    private List<Payments> paymentList;
 
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Account account = new Account();  // Assuming Account class exists
        account.setAccountid(1L);
        payment = new Payments();
        payment.setPaymentid(1L);
        payment.setAccount(account);
        payment.setPaymentDate(LocalDate.now());
        payment.setAmount(100);
        payment.setMethod(Epaymethod.CREDIT_CARD);  // Assuming Epaymethod enum exists
 
        paymentList = Arrays.asList(payment);
    }
 
    @Test
    public void testAddPayment_Success() {
        when(paymentService.addPayments(payment)).thenReturn(payment);
        ResponseEntity<Payments> response = paymentController.addPayment(payment);
 
        verify(paymentService).addPayments(payment);
        assert(response.getStatusCode() == HttpStatus.CREATED);
        assert(response.getBody().equals(payment));
    }
 
    @Test
    public void testGetAllPayments_Success() {
        when(paymentService.getAllPayments()).thenReturn(paymentList);
        ResponseEntity<List<Payments>> response = paymentController.getAllPayments();
 
        verify(paymentService).getAllPayments();
        assert(response.getStatusCode() == HttpStatus.OK);
        assert(response.getBody().equals(paymentList));
    }
 
    @Test
    public void testGetPayment_Success() throws PaymentNotFoundException {
        Long id = 1L;
        when(paymentService.getPayment(id)).thenReturn(payment);
        ResponseEntity<Payments> response = (ResponseEntity<Payments>) paymentController.getPayment(id);
 
        verify(paymentService).getPayment(id);
        assert(response.getStatusCode() == HttpStatus.OK);
        assert(response.getBody().equals(payment));
    }
 
    @Test
    public void testUpdatePayment_Success() throws PaymentNotFoundException {
        when(paymentService.updatePayments(payment)).thenReturn(payment);
        ResponseEntity<Payments> response = (ResponseEntity<Payments>) paymentController.updatePayment(payment);
 
        verify(paymentService).updatePayments(payment);
        assert(response.getStatusCode() == HttpStatus.OK);
        assert(response.getBody().equals(payment));
    }
 
    @Test
    public void testDeletePayment_Success() throws PaymentNotFoundException {
        Long id = 1L;
        doNothing().when(paymentService).deletePayment(id);
        ResponseEntity<Void> response = (ResponseEntity<Void>) paymentController.deletePayment(id);
 
        verify(paymentService).deletePayment(id);
        assert(response.getStatusCode() == HttpStatus.NO_CONTENT);
    }
 
    @Test
    public void testGetPayment_NotFound() throws PaymentNotFoundException {
        Long id = 2L;
        when(paymentService.getPayment(id)).thenThrow(PaymentNotFoundException.class);
        ResponseEntity<?> response = paymentController.getPayment(id);
 
        verify(paymentService).getPayment(id);
        assert(response.getStatusCode() == HttpStatus.NOT_FOUND);
    }
 
    @Test
    public void testUpdatePayment_NotFound() throws PaymentNotFoundException {
        when(paymentService.updatePayments(payment)).thenThrow(PaymentNotFoundException.class);
        ResponseEntity<?> response = paymentController.updatePayment(payment);
 
        verify(paymentService).updatePayments(payment);
        assert(response.getStatusCode() == HttpStatus.NOT_FOUND);
    }
 
    @Test
    public void testDeletePayment_NotFound() throws PaymentNotFoundException {
        Long id = 2L;
        doThrow(PaymentNotFoundException.class).when(paymentService).deletePayment(id);
        ResponseEntity<?> response = paymentController.deletePayment(id);
 
        verify(paymentService).deletePayment(id);
        assert(response.getStatusCode() == HttpStatus.NOT_FOUND);
    }

}
