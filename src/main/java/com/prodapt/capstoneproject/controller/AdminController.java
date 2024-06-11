package com.prodapt.capstoneproject.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.prodapt.capstoneproject.entities.Admin;
import com.prodapt.capstoneproject.entities.Customer;
import com.prodapt.capstoneproject.entities.EMessage;
import com.prodapt.capstoneproject.entities.EReport;
import com.prodapt.capstoneproject.entities.EResponse;
import com.prodapt.capstoneproject.entities.Notification;
import com.prodapt.capstoneproject.entities.Reports;
import com.prodapt.capstoneproject.exceptions.AccountNotFoundException;
import com.prodapt.capstoneproject.exceptions.AdminNotFoundException;
import com.prodapt.capstoneproject.model.AdminActionsReport;
import com.prodapt.capstoneproject.model.CustomerStatusReport;
import com.prodapt.capstoneproject.model.DunningReport;
import com.prodapt.capstoneproject.model.ExceptionReport;
import com.prodapt.capstoneproject.model.PaymentDetails;
import com.prodapt.capstoneproject.model.PerformanceDashboardReport;
import com.prodapt.capstoneproject.model.UsersList;
import com.prodapt.capstoneproject.service.AccountService;
import com.prodapt.capstoneproject.service.AdminService;
import com.prodapt.capstoneproject.service.CustomerService;
import com.prodapt.capstoneproject.service.NotificationService;
import com.prodapt.capstoneproject.service.PaymentService;
import com.prodapt.capstoneproject.service.ReportService;

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

    @PostMapping("/updateadmin")
    public ResponseEntity<Admin> updateCustomer(@RequestBody Admin admin) throws AdminNotFoundException {
        Admin updatedAdmin = aservice.updateAdmin(admin);
        return ResponseEntity.ok(updatedAdmin);
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
    public ResponseEntity<List<CustomerStatusReport>> getCustomerStatusReport(@PathVariable Long adminid) {
        List<CustomerStatusReport> report = cservice.getCustomerStatusReport();
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/getdunningreport/{adminid}")
    public ResponseEntity<List<DunningReport>> getDunningReport(@PathVariable Long adminid) {
        List<DunningReport> report = nservice.getDunningReport();
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/getpaymentdetailsreport/{adminid}")
    public ResponseEntity<List<PaymentDetails>> getPaymentDetailsReport(@PathVariable Long adminid) {
        List<PaymentDetails> report = pservice.getPaymentDetailsReport();
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/getexceptionreport//{adminid}")
    public ResponseEntity<List<ExceptionReport>> getExceptionReport(@PathVariable Long adminid) {
        List<ExceptionReport> report = nservice.getExceptionReport();
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/getperfomancedashboardreport//{adminid}")
    public ResponseEntity<List<PerformanceDashboardReport>> getPerformanceDashboardReport(@PathVariable Long adminid) {
        List<PerformanceDashboardReport> report = nservice.getPerformanceDashboardReport();
        return ResponseEntity.ok(report);
    }
    
    @PostMapping("/sendnotification")
    public ResponseEntity<String> sendCustomernotification(@RequestBody UsersList users) throws AccountNotFoundException{
        for(Customer user: users.getCustomers()) {
        	Notification notification = new Notification();
        	notification.setAccount(accservice.findAccountByCustomerId(user.getId()));
        	notification.setMethod(EMessage.EMAIL);
        	notification.setResponse(EResponse.valueOf(new Random().nextInt(EResponse.values().length)));
        	notification.setSendDate(LocalDate.now());
        	nservice.addNotification(notification);
        }
        return ResponseEntity.ok("Notification send to the all customers");
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
