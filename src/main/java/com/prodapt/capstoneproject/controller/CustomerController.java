package com.prodapt.capstoneproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.prodapt.capstoneproject.entities.ERole;
import com.prodapt.capstoneproject.entities.UserEntity;
import com.prodapt.capstoneproject.exceptions.CustomerNotFoundException;
import com.prodapt.capstoneproject.repositories.UserRepository;
import com.prodapt.capstoneproject.service.CustomerService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@Transactional
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
    CustomerService cservice;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder encoder;	
	
 
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
    public ResponseEntity<Customer> updateCustomer(@RequestBody @Valid Customer customer) {
        try {
            Customer updatedCustomer = cservice.updateCustomer(customer);

            UserEntity user = new UserEntity();
            user.setUsername(customer.getUsername());
            user.setPassword(encoder.encode(customer.getPassword()));
            user.setRole(ERole.CUSTOMER);
            userRepository.save(user);

            return ResponseEntity.ok(updatedCustomer);
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
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
    
    @GetMapping("/getcustomer/{username}")
    public ResponseEntity<Customer> getCustomerByUsername(@PathVariable String username) {
        try {
            Customer customer = cservice.findCustomerbyUsername(username);
            return ResponseEntity.ok(customer);
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}