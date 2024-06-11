package com.prodapt.capstoneproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prodapt.capstoneproject.entities.Customer;
import com.prodapt.capstoneproject.exceptions.CustomerNotFoundException;
import com.prodapt.capstoneproject.service.CustomerService;
import jakarta.transaction.Transactional;

@RestController
@Transactional
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
    CustomerService cservice;
 
    // Create Operations
    @PostMapping("/addcustomer")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        Customer newCustomer = cservice.addCustomer(customer);
        return new ResponseEntity<>(newCustomer, HttpStatus.OK);
    }
 
    // Read Operations
    @GetMapping("/getallcustomers")
    public ResponseEntity<Iterable<Customer>> getAllCustomers() {
        Iterable<Customer> customers = cservice.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
 
    @GetMapping("/getcustomer/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Integer id) throws CustomerNotFoundException {
        Customer customer = cservice.findCustomer(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
 
    // Update Operations
    @PutMapping("/updatecustomer")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) throws CustomerNotFoundException {
        Customer updatedCustomer = cservice.updateCustomer(customer);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }
 
    // Delete Operations
    @DeleteMapping("/deletecustomer/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) throws CustomerNotFoundException {
        cservice.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
 
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}