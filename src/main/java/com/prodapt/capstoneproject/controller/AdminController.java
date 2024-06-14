package com.prodapt.capstoneproject.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.prodapt.capstoneproject.entities.Admin;
import com.prodapt.capstoneproject.entities.Customer;
import com.prodapt.capstoneproject.entities.EMessage;
import com.prodapt.capstoneproject.entities.EReport;
import com.prodapt.capstoneproject.entities.EResponse;
import com.prodapt.capstoneproject.entities.Notification;
import com.prodapt.capstoneproject.entities.Reports;
import com.prodapt.capstoneproject.entities.UserEntity;
import com.prodapt.capstoneproject.exceptions.AccountNotFoundException;
import com.prodapt.capstoneproject.exceptions.AdminNotFoundException;
import com.prodapt.capstoneproject.model.AdminActionsReport;
import com.prodapt.capstoneproject.model.CustomerStatusReport;
import com.prodapt.capstoneproject.model.DunningReport;
import com.prodapt.capstoneproject.model.ExceptionReport;
import com.prodapt.capstoneproject.model.PaymentDetails;
import com.prodapt.capstoneproject.model.PerformanceDashboardReport;
import com.prodapt.capstoneproject.model.UsersList;
import com.prodapt.capstoneproject.repositories.UserRepository;
import com.prodapt.capstoneproject.service.AccountService;
import com.prodapt.capstoneproject.service.AdminService;
import com.prodapt.capstoneproject.service.CustomerService;
import com.prodapt.capstoneproject.service.NotificationService;
import com.prodapt.capstoneproject.service.PaymentService;
import com.prodapt.capstoneproject.service.ReportService;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService aservice;
    
    @Autowired
    private NotificationService nservice;
    
    @Autowired
    private AccountService accservice;
    
    @Autowired
    private CustomerService cservice;
    
    @Autowired
    private PaymentService pservice;
    
    @Autowired
    private ReportService rservice;
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/updateadmin")
    public ResponseEntity<Admin> updateAdmin(@RequestBody @Valid Admin admin) {
        try {
            Admin updatedAdmin = aservice.updateAdmin(admin);
            updateUserEntity(admin);
            return ResponseEntity.ok(updatedAdmin);
        } catch (AdminNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } 
    }
    
    private void updateUserEntity(Admin admin) {
        UserEntity user = new UserEntity();
        user.setUsername(admin.getUsername());
        user.setPassword(encoder.encode(admin.getPassword()));
        user.setRole(admin.getRole());
        userRepository.save(user);
    }
    @GetMapping("/findall")
    public ResponseEntity<List<Admin>> findAllCustomers() {
        List<Admin> adminList = aservice.findAll();
        return ResponseEntity.ok(adminList);
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<Admin> findCustomerById(@PathVariable Long id) throws AdminNotFoundException {
        Admin admin = aservice.findById(id);
        return ResponseEntity.ok(admin);
    }

    @DeleteMapping("/deletebyid/{id}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable Long id) throws AdminNotFoundException {
        aservice.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity<Void> deleteAllCustomers() {
        aservice.deleteAll();
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/getadminactionreport/{adminid}")
    public ResponseEntity<List<AdminActionsReport>> getAdminActionReport(@PathVariable Long adminid) throws AdminNotFoundException {
        List<AdminActionsReport> report = aservice.getAdminActionReport();
        Reports newreport  = new Reports();
        newreport.setAdmin(aservice.findById(adminid));
        newreport.setGeneratedDate(LocalDate.now());
        newreport.setType(EReport.ADMINSTRATIVE);
        rservice.addReport(newreport);
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/getstatusreport/{adminid}")
    public ResponseEntity<List<CustomerStatusReport>> getCustomerStatusReport(@PathVariable Long adminid) throws AdminNotFoundException {
        List<CustomerStatusReport> report = cservice.getCustomerStatusReport();
        Reports newreport  = new Reports();
        newreport.setAdmin(aservice.findById(adminid));
        newreport.setGeneratedDate(LocalDate.now());
        newreport.setType(EReport.CUSTOMER_ACCOUT_STATUS);
        rservice.addReport(newreport);
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/getdunningreport/{adminid}")
    public ResponseEntity<List<DunningReport>> getDunningReport(@PathVariable Long adminid) throws AdminNotFoundException {
        List<DunningReport> report = nservice.getDunningReport();
        Reports newreport  = new Reports();
        newreport.setAdmin(aservice.findById(adminid));
        newreport.setGeneratedDate(LocalDate.now());
        newreport.setType(EReport.DUNNING_REPORT);
        rservice.addReport(newreport);
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/getpaymentdetailsreport/{adminid}")
    public ResponseEntity<List<PaymentDetails>> getPaymentDetailsReport(@PathVariable Long adminid) throws AdminNotFoundException {
        List<PaymentDetails> report = pservice.getPaymentDetailsReport();
        Reports newreport  = new Reports();
        newreport.setAdmin(aservice.findById(adminid));
        newreport.setGeneratedDate(LocalDate.now());
        newreport.setType(EReport.PAYMENT_HISTORY);
        rservice.addReport(newreport);
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/getexceptionreport//{adminid}")
    public ResponseEntity<List<ExceptionReport>> getExceptionReport(@PathVariable Long adminid) throws AdminNotFoundException {
        List<ExceptionReport> report = nservice.getExceptionReport();
        Reports newreport  = new Reports();
        newreport.setAdmin(aservice.findById(adminid));
        newreport.setGeneratedDate(LocalDate.now());
        newreport.setType(EReport.EXCEPTION_REPORT);
        rservice.addReport(newreport);
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/getperfomancedashboardreport//{adminid}")
    public ResponseEntity<List<PerformanceDashboardReport>> getPerformanceDashboardReport(@PathVariable Long adminid) throws AdminNotFoundException {
        List<PerformanceDashboardReport> report = nservice.getPerformanceDashboardReport();
        Reports newreport  = new Reports();
        newreport.setAdmin(aservice.findById(adminid));
        newreport.setGeneratedDate(LocalDate.now());
        newreport.setType(EReport.PERFORMANCE_DASHBOARD);
        rservice.addReport(newreport);
        return ResponseEntity.ok(report);
    }
    
    @PostMapping("/sendnotification")
    public ResponseEntity<String> sendCustomernotification(@RequestBody UsersList users) {
        try {
            for (Customer user : users.getCustomers()) {
                Notification notification = new Notification();
                notification.setAccount(accservice.findAccountByCustomerId(user.getId()));
                notification.setMethod(EMessage.EMAIL);
                notification.setResponse(EResponse.valueOf(new Random().nextInt(EResponse.values().length)));
                notification.setSendDate(LocalDate.now());
                nservice.addNotification(notification);
            }
            return ResponseEntity.ok("Notification sent to all customers");
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
    }
    
    @GetMapping("/getadmin/{username}")
    public ResponseEntity<Admin> getAdminByUsername(@PathVariable String username) {
        try {
        	Admin admin = aservice.findByUsername(username);
            return ResponseEntity.ok(admin);
        } catch (AdminNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
     
}
