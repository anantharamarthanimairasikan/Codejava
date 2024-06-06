package com.prodapt.capstoneproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prodapt.capstoneproject.entities.Account;
import com.prodapt.capstoneproject.entities.Customer;
import com.prodapt.capstoneproject.entities.Notification;
import com.prodapt.capstoneproject.entities.Payments;
import com.prodapt.capstoneproject.exceptions.AccountNotFoundException;
import com.prodapt.capstoneproject.exceptions.CustomerNotFoundException;
import com.prodapt.capstoneproject.exceptions.NotificationNotFoundException;
import com.prodapt.capstoneproject.exceptions.PaymentNotFoundException;
import com.prodapt.capstoneproject.model.CustomerStatusReport;
import com.prodapt.capstoneproject.service.AccountService;
import com.prodapt.capstoneproject.service.CustomerService;
import com.prodapt.capstoneproject.service.NotificationService;
import com.prodapt.capstoneproject.service.PaymentService;

import jakarta.transaction.Transactional;

@RestController
@Transactional
@RequestMapping("/customer")
public class CustomerController {
    
    @Autowired
    CustomerService cservice;

    @Autowired
    AccountService aservice;

    @Autowired
    NotificationService nservice;

    @Autowired
    PaymentService pservice;

    // Create Operations
    @PostMapping("/addcustomer")
    public Customer addCustomer(@RequestBody Customer customer) {
        return cservice.addCustomer(customer);
    }

    @PostMapping("/addaccount")
    public Account addAccount(@RequestBody Account account) {
        return aservice.addAccount(account);
    }

    @PostMapping("/addpayment")
    public Payments addPayment(@RequestBody Payments payment) {
        return pservice.addPayments(payment);
    }

    @PostMapping("/addnotification")
    public Notification addNotification(@RequestBody Notification notification) {
        return nservice.addNotification(notification);
    }

    // Read Operations
    @GetMapping("/getallcustomers")
    public Iterable<Customer> getAllCustomers() {
        return cservice.getAllCustomers();
    }

    @GetMapping("/getallaccounts")
    public Iterable<Account> getAllAccounts() {
        return aservice.getAllAccounts();
    }

    @GetMapping("/getallpayments")
    public Iterable<Payments> getAllPayments() {
        return pservice.getAllPayments();
    }

    @GetMapping("/getallnotifications")
    public Iterable<Notification> getAllNotifications() {
        return nservice.getAllNotifications();
    }

    @GetMapping("/getcustomer/{id}")
    public Customer getCustomer(@PathVariable Integer id) throws CustomerNotFoundException {
        return cservice.getCustomer(id);
    }

    @GetMapping("/getaccount/{id}")
    public Account getAccount(@PathVariable Long id) throws AccountNotFoundException {
        return aservice.getAccount(id);
    }

    @GetMapping("/getpayment/{id}")
    public Payments getPayment(@PathVariable Long id) throws PaymentNotFoundException {
        return pservice.getPayment(id);
    }

    @GetMapping("/getnotification/{id}")
    public Notification getNotification(@PathVariable Long id) throws NotificationNotFoundException {
        return nservice.getNotification(id);
    }
    @GetMapping("/getstatusreport")
    public List<CustomerStatusReport> getCustomerStatusReport(){
        return cservice.getCustomerStatusReport();
    }

    // Update Operations
    @PutMapping("/updatecustomer")
    public Customer updateCustomer(@RequestBody Customer customer) throws CustomerNotFoundException {
        return cservice.updateCustomer(customer);
    }

    @PutMapping("/updateaccount")
    public Account updateAccount(@RequestBody Account account) throws AccountNotFoundException {
        return aservice.updateAccount(account);
    }

    @PutMapping("/updatepayment")
    public Payments updatePayment(@RequestBody Payments payment) throws PaymentNotFoundException {
        return pservice.updatePayments(payment);
    }

    @PutMapping("/updatenotification")
    public Notification updateNotification(@RequestBody Notification notification) throws NotificationNotFoundException {
        return nservice.updateNotification(notification);
    }

    // Delete Operations
    @DeleteMapping("/deletecustomer/{id}")
    public void deleteCustomer(@PathVariable Integer id) throws CustomerNotFoundException {
        cservice.deleteCustomer(id);
    }

    @DeleteMapping("/deleteaccount/{id}")
    public void deleteAccount(@PathVariable Long id) throws AccountNotFoundException {
        aservice.deleteAccount(id);
    }

    @DeleteMapping("/deletepayment/{id}")
    public void deletePayment(@PathVariable Long id) throws PaymentNotFoundException {
        pservice.deletePayment(id);
    }

    @DeleteMapping("/deletenotification/{id}")
    public void deleteNotification(@PathVariable Long id) throws NotificationNotFoundException {
        nservice.deleteNotification(id);
    }
}