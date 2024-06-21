package com.prodapt.capstoneproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.prodapt.capstoneproject.entities.*;
import com.prodapt.capstoneproject.exceptions.*;
import com.prodapt.capstoneproject.security.service.UserEntityService;
import com.prodapt.capstoneproject.service.*;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@Transactional
@RequestMapping("/api/customer")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserEntityService userEntityService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Create a new customer
    @PostMapping("/addcustomer")
    public ResponseEntity<Customer> addCustomer(@RequestBody @Valid Customer customer) {
        Customer newCustomer = customerService.addCustomer(customer);
        return ResponseEntity.ok(newCustomer);
    }

    // Retrieve all customers
    @GetMapping("/getallcustomers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    // Retrieve a customer by ID
    @GetMapping("/getcustomer/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Integer id) {
        try {
            Customer customer = customerService.findCustomer(id);
            return ResponseEntity.ok(customer);
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Update a customer's information
    @PutMapping("/updatecustomer")
    public ResponseEntity<Customer> updateCustomer(@RequestBody @Valid Customer customer) throws Exception {
        try {
            Customer updatedCustomer = customerService.updateCustomer(customer);

            UserEntity user = new UserEntity();
            user.setUsername(customer.getUsername());
            user.setPassword(passwordEncoder.encode(customer.getPassword()));
            user.setRole(ERole.CUSTOMER);
            userEntityService.updateUser(user);

            return ResponseEntity.ok(updatedCustomer);
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Delete a customer by ID
    @DeleteMapping("/deletecustomer/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        try {
            Customer customer = customerService.findCustomer(id);
            userEntityService.deletebyusername(customer.getUsername());

            Account account = accountService.findAccountByCustomerId(id);
            accountService.deleteAccount(account.getAccountid());

            customerService.deleteCustomer(id);
            return ResponseEntity.noContent().build();
        } catch (CustomerNotFoundException | AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Retrieve a customer by username
    @GetMapping("/getcustomerbyusername/{username}")
    public ResponseEntity<Customer> getCustomerByUsername(@PathVariable String username) {
        try {
            Customer customer = customerService.findCustomerbyUsername(username);
            return ResponseEntity.ok(customer);
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

   
}
