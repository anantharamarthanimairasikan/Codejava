package com.prodapt.capstoneproject.controllertests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.prodapt.capstoneproject.controller.CustomerController;
import com.prodapt.capstoneproject.entities.*;
import com.prodapt.capstoneproject.exceptions.*;
import com.prodapt.capstoneproject.security.service.UserEntityService;
import com.prodapt.capstoneproject.service.*;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private UserEntityService userEntityService;

    @Mock
    private AccountService accountService;

    @Mock
    private PaymentService paymentService;

    @Mock
    private NotificationService notificationService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomerController customerController;

    private Customer customer;
    private UserEntity user;
    private Account account;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
        customer.setId(1);
        customer.setUsername("testuser");
        customer.setPassword("password");

        user = new UserEntity();
        user.setUsername("testuser");
        user.setPassword("encodedPassword");
        user.setRole(ERole.CUSTOMER);

        account = new Account();
        account.setAccountid(1);
        account.setCustomer(customer);
        account.setNotifications(Collections.emptyList());
        account.setPayments(Collections.emptyList());
    }

    @Test
    public void testAddCustomer_Success() {
        when(customerService.addCustomer(any(Customer.class))).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.addCustomer(customer);

        verify(customerService).addCustomer(customer);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
        assert response.getBody().getUsername().equals("testuser");
    }

    @Test
    public void testGetAllCustomers_Success() {
        when(customerService.getAllCustomers()).thenReturn(Collections.singletonList(customer));

        ResponseEntity<List<Customer>> response = customerController.getAllCustomers();

        verify(customerService).getAllCustomers();
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
        assert response.getBody().iterator().next().getUsername().equals("testuser");
    }

    @Test
    public void testGetCustomerById_Success() throws CustomerNotFoundException {
        when(customerService.findCustomer(anyInt())).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.getCustomer(1);

        verify(customerService).findCustomer(1);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
        assert response.getBody().getUsername().equals("testuser");
    }

    @Test
    public void testGetCustomerById_Failure() throws CustomerNotFoundException {
        when(customerService.findCustomer(anyInt())).thenThrow(new CustomerNotFoundException("Customer not found"));

        ResponseEntity<Customer> response = customerController.getCustomer(1);

        verify(customerService).findCustomer(1);
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
    }

    @Test
    public void testUpdateCustomer_Success() throws Exception {
        // Mock the behavior of customerService.updateCustomer to return the customer
        when(customerService.updateCustomer(any(Customer.class))).thenReturn(customer);
        
        // Mock the behavior of passwordEncoder.encode to return an encoded password
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        
        ResponseEntity<Customer> response = customerController.updateCustomer(customer);

        // Verify the interactions with the mocked services
        verify(customerService).updateCustomer(customer);
        verify(userEntityService).updateUser(any(UserEntity.class));
        
        // Assert the response status and body
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
        assert response.getBody().getUsername().equals("testuser");
    }


    @Test
    public void testUpdateCustomer_Failure() throws CustomerNotFoundException,Exception {
        when(customerService.updateCustomer(any(Customer.class))).thenThrow(new CustomerNotFoundException("Customer not found"));

        ResponseEntity<Customer> response = customerController.updateCustomer(customer);

        verify(customerService).updateCustomer(customer);
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
    }

    @Test
    public void testDeleteCustomer_Success() throws CustomerNotFoundException, AccountNotFoundException {
        when(customerService.findCustomer(anyInt())).thenReturn(customer);
        when(accountService.findAccountByCustomerId(anyInt())).thenReturn(account);
        doNothing().when(userEntityService).deletebyusername(any(String.class));
        doNothing().when(accountService).deleteAccount(anyLong());
        doNothing().when(customerService).deleteCustomer(anyInt());

        ResponseEntity<Void> response = customerController.deleteCustomer(1);

        verify(customerService).findCustomer(1);
        verify(userEntityService).deletebyusername("testuser");
        verify(accountService).deleteAccount(1L);
        verify(customerService).deleteCustomer(1);
        assert response.getStatusCode() == HttpStatus.NO_CONTENT;
    }

    @Test
    public void testDeleteCustomer_Failure() throws CustomerNotFoundException, AccountNotFoundException {
        when(customerService.findCustomer(anyInt())).thenThrow(new CustomerNotFoundException("Customer not found"));

        ResponseEntity<Void> response = customerController.deleteCustomer(1);

        verify(customerService).findCustomer(1);
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
    }

    @Test
    public void testGetCustomerByUsername_Success() throws CustomerNotFoundException {
        when(customerService.findCustomerbyUsername(any(String.class))).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.getCustomerByUsername("testuser");

        verify(customerService).findCustomerbyUsername("testuser");
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
        assert response.getBody().getUsername().equals("testuser");
    }

    @Test
    public void testGetCustomerByUsername_Failure() throws CustomerNotFoundException {
        when(customerService.findCustomerbyUsername(any(String.class))).thenThrow(new CustomerNotFoundException("Customer not found"));

        ResponseEntity<Customer> response = customerController.getCustomerByUsername("testuser");

        verify(customerService).findCustomerbyUsername("testuser");
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
    }
}
