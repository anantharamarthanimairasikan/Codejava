package com.prodapt.capstoneproject.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.prodapt.capstoneproject.entities.Admin;
import com.prodapt.capstoneproject.entities.EReport;
import com.prodapt.capstoneproject.entities.Reports;
import com.prodapt.capstoneproject.entities.UserEntity;
import com.prodapt.capstoneproject.exceptions.AdminNotFoundException;
import com.prodapt.capstoneproject.model.*;
import com.prodapt.capstoneproject.security.service.UserEntityService;
import com.prodapt.capstoneproject.service.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminController {

    @Autowired
    private AdminService adminService;
    
    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private ReportService reportService;
    
    @Autowired
    private UserEntityService userEntityService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Updates an existing admin
    @PostMapping("/updateadmin")
    public ResponseEntity<Admin> updateAdmin(@RequestBody @Valid Admin admin) {
        try {
            Admin updatedAdmin = adminService.updateAdmin(admin);
            updateUserEntity(admin);
            return ResponseEntity.ok(updatedAdmin);
        } catch (AdminNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    // Helper method to update the corresponding UserEntity when an Admin is updated
    private void updateUserEntity(Admin admin) {
        UserEntity user = new UserEntity();
        user.setUsername(admin.getUsername());
        user.setPassword(passwordEncoder.encode(admin.getPassword()));
        user.setRole(admin.getRole());
        userEntityService.addUserEntity(user);
    }
    
    // Fetches all admins
    @GetMapping("/findall")
    public ResponseEntity<List<Admin>> findAllAdmins() {
        List<Admin> adminList = adminService.findAll();
        return ResponseEntity.ok(adminList);
    }

    // Fetches an admin by ID
    @GetMapping("/findbyid/{id}")
    public ResponseEntity<Admin> findAdminById(@PathVariable Long id) {
        try {
            Admin admin = adminService.findById(id);
            return ResponseEntity.ok(admin);
        } catch (AdminNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Deletes an admin by ID
    @DeleteMapping("/deletebyid/{id}")
    public ResponseEntity<Void> deleteAdminById(@PathVariable Long id) {
        try {
            Admin admin = adminService.findById(id);
            userEntityService.deletebyusername(admin.getUsername());
            adminService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (AdminNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Deletes all admins
    @DeleteMapping("/deleteall")
    public ResponseEntity<Void> deleteAllAdmins() {
        adminService.deleteAll();
        return ResponseEntity.noContent().build();
    }

    // Generates and fetches customer status report
    @GetMapping("/getstatusreport/{adminid}")
    public ResponseEntity<List<CustomerStatusReport>> getCustomerStatusReport(@PathVariable Long adminid) throws AdminNotFoundException {
        List<CustomerStatusReport> report = customerService.getCustomerStatusReport();
        Reports newreport  = new Reports();
        newreport.setAdmin(adminService.findById(adminid));
        newreport.setGeneratedDate(LocalDate.now());
        newreport.setType(EReport.CUSTOMER_ACCOUT_STATUS);
        reportService.addReport(newreport);
        return ResponseEntity.ok(report);
    }
    
    // Generates and fetches dunning report
    @GetMapping("/getdunningreport/{adminid}")
    public ResponseEntity<List<DunningReport>> getDunningReport(@PathVariable Long adminid) throws AdminNotFoundException {
        List<DunningReport> report = notificationService.getDunningReport();
        Reports newreport  = new Reports();
        newreport.setAdmin(adminService.findById(adminid));
        newreport.setGeneratedDate(LocalDate.now());
        newreport.setType(EReport.DUNNING_REPORT);
        reportService.addReport(newreport);
        return ResponseEntity.ok(report);
    }

    // Generates and fetches payment details report
    @GetMapping("/getpaymentdetailsreport/{adminid}")
    public ResponseEntity<List<PaymentDetails>> getPaymentDetailsReport(@PathVariable Long adminid) throws AdminNotFoundException {
        List<PaymentDetails> report = paymentService.getPaymentDetailsReport();
        Reports newreport  = new Reports();
        newreport.setAdmin(adminService.findById(adminid));
        newreport.setGeneratedDate(LocalDate.now());
        newreport.setType(EReport.PAYMENT_HISTORY);
        reportService.addReport(newreport);
        return ResponseEntity.ok(report);
    }
    

    // Generates and fetches performance dashboard report
    @GetMapping("/getperfomancedashboardreport//{adminid}")
    public ResponseEntity<List<PerformanceDashboardReport>> getPerformanceDashboardReport(@PathVariable Long adminid) throws AdminNotFoundException {
        List<PerformanceDashboardReport> report = notificationService.getPerformanceDashboardReport();
        Reports newreport  = new Reports();
        newreport.setAdmin(adminService.findById(adminid));
        newreport.setGeneratedDate(LocalDate.now());
        newreport.setType(EReport.PERFORMANCE_DASHBOARD);
        reportService.addReport(newreport);
        return ResponseEntity.ok(report);
    }

    // Fetches an admin by username
    @GetMapping("/getadmin/{username}")
    public ResponseEntity<Admin> getAdminByUsername(@PathVariable String username) {
        try {
            Admin admin = adminService.findByUsername(username);
            return ResponseEntity.ok(admin);
        } catch (AdminNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
