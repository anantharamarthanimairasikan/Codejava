package com.prodapt.capstoneproject.controllertests;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
 
import static org.mockito.Mockito.doNothing;
 
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

import com.prodapt.capstoneproject.controller.AccountController;
import com.prodapt.capstoneproject.entities.Account;
import com.prodapt.capstoneproject.entities.Customer;
import com.prodapt.capstoneproject.entities.Payments;
import com.prodapt.capstoneproject.exceptions.AccountNotFoundException;
import com.prodapt.capstoneproject.model.PaymentReq;
import com.prodapt.capstoneproject.service.AccountService;
import com.prodapt.capstoneproject.service.PaymentService;

import static org.junit.jupiter.api.Assertions.*;

 
public class AccountControllerTest {
 
    @InjectMocks
    private AccountController accountController;
 
    @Mock
    private AccountService accountService;
 
    @Mock
    private PaymentService paymentService;
 
    private Account account;
    private List<Account> accountList;
 
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
 
        // Create a mock account
        account = new Account();
        account.setAccountid(1L);
        account.setCustomer(new Customer());
        account.setCreationDate(LocalDate.now());
        account.setAmount(100);
 
        // Mock account list
        accountList = Arrays.asList(account);
    }
 
    @Test
    public void testGetAllAccounts_Success() {
        when(accountService.getAllAccounts()).thenReturn(accountList);
        ResponseEntity<List<Account>> response = accountController.getAllAccounts();
 
        verify(accountService).getAllAccounts();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accountList, response.getBody());
    }
 
    @Test
    public void testAddAccount_Success() {
        when(accountService.addAccount(account)).thenReturn(account);
        ResponseEntity<Account> response = accountController.addAccount(account);
 
        verify(accountService).addAccount(account);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(account, response.getBody());
    }
 
    @Test
    public void testGetAccount_Success() throws AccountNotFoundException {
        Long id = 1L;
        when(accountService.findAccount(id)).thenReturn(account);
        ResponseEntity<Account> response = accountController.getAccount(id);
 
        verify(accountService).findAccount(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(account, response.getBody());
    }
 
    @Test
    public void testUpdateAccount_Success() throws AccountNotFoundException {
        when(accountService.updateAccount(account)).thenReturn(account);
        ResponseEntity<Account> response = accountController.updateAccount(account);
 
        verify(accountService).updateAccount(account);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(account, response.getBody());
    }
 
    @Test
    public void testDeleteAccount_Success() throws AccountNotFoundException {
        Long id = 1L;
        doNothing().when(accountService).deleteAccount(id);
        ResponseEntity<Void> response = accountController.deleteAccount(id);
 
        verify(accountService).deleteAccount(id);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
 
    @Test
    public void testMakePayment_Success() throws AccountNotFoundException {
        PaymentReq paymentReq = new PaymentReq();
        paymentReq.setAccountid(1L);
        paymentReq.setAmount(50);
 
        when(accountService.findAccount(paymentReq.getAccountid())).thenReturn(account);
        ResponseEntity<Payments> response = accountController.makepayment(paymentReq);
 
        verify(accountService).findAccount(paymentReq.getAccountid());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getMethod()); // Payment method should not be asserted
        assertEquals(paymentReq.getAmount(), response.getBody().getAmount());
        assertNotNull(response.getBody().getPaymentDate());
    }
 
 
    @Test
    public void testMakePayment_AccountNotFound() throws AccountNotFoundException {
        PaymentReq paymentReq = new PaymentReq();
        paymentReq.setAccountid(2L); // Account id that doesn't exist
        paymentReq.setAmount(50);
 
        when(accountService.findAccount(paymentReq.getAccountid())).thenThrow(new AccountNotFoundException("Account not found"));
        ResponseEntity<Payments> response = accountController.makepayment(paymentReq);
 
        verify(accountService).findAccount(paymentReq.getAccountid());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
 
}
