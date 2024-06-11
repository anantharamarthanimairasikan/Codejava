package com.prodapt.capstoneproject.controllertests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.prodapt.capstoneproject.controller.AdminController;
import com.prodapt.capstoneproject.entities.Account;
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

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @MockBean
    private NotificationService notificationService;

    @MockBean
    private AccountService accountService;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private ReportService reportService;

    @InjectMocks
    private AdminController adminController;

    private Admin admin;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        admin = new Admin();
        admin.setAdminid(1L);
        admin.setUsername("testadmin");
        admin.setPassword("password");
    }

    @Test
    void testUpdateCustomer_Success() throws Exception {
        when(adminService.updateAdmin(any(Admin.class))).thenReturn(admin);
        ResponseEntity<Admin> response = adminController.updateCustomer(admin);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(admin, response.getBody());
    }

    @Test
    void testUpdateCustomer_AdminNotFound() throws Exception {
        doThrow(new AdminNotFoundException("Admin not found")).when(adminService).updateAdmin(any(Admin.class));
        assertThrows(AdminNotFoundException.class, () -> adminController.updateCustomer(admin));
    }

    @Test
    void testFindAllCustomers_Success() throws Exception {
        List<Admin> adminList = new ArrayList<>();
        adminList.add(admin);
        when(adminService.findAll()).thenReturn(adminList);
        ResponseEntity<List<Admin>> response = adminController.findAllCustomers();
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(adminList, response.getBody());
    }

    @Test
    void testFindCustomerById_Success() throws Exception {
        when(adminService.findById(1L)).thenReturn(admin);
        ResponseEntity<Admin> response = adminController.findCustomerById(1L);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(admin, response.getBody());
    }

    @Test
    void testFindCustomerById_AdminNotFound() throws Exception {
        doThrow(new AdminNotFoundException("Admin not found")).when(adminService).findById(1L);
        assertThrows(AdminNotFoundException.class, () -> adminController.findCustomerById(1L));
    }

    @Test
    void testDeleteCustomerById_Success() throws Exception {
        doNothing().when(adminService).delete(1L);
        ResponseEntity<Void> response = adminController.deleteCustomerById(1L);
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testDeleteCustomerById_AdminNotFound() throws Exception {
        doThrow(new AdminNotFoundException("Admin not found")).when(adminService).delete(1L);
        assertThrows(AdminNotFoundException.class, () -> adminController.deleteCustomerById(1L));
    }

    @Test
    void testDeleteAllCustomers_Success() throws Exception {
        doNothing().when(adminService).deleteAll();
        ResponseEntity<Void> response = adminController.deleteAllCustomers();
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testGetAdminActionReport_Success() throws Exception {
        List<AdminActionsReport> report = new ArrayList<>();
        when(adminService.getAdminActionReport()).thenReturn(report);
        when(adminService.findById(1L)).thenReturn(admin);
        ResponseEntity<List<AdminActionsReport>> response = adminController.getAdminActionReport(1L);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(report, response.getBody());
    }

    @Test
    void testGetCustomerStatusReport_Success() throws Exception {
        List<CustomerStatusReport> report = new ArrayList<>();
        when(customerService.getCustomerStatusReport()).thenReturn(report);
        ResponseEntity<List<CustomerStatusReport>> response = adminController.getCustomerStatusReport(1L);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(report, response.getBody());
    }

    @Test
    void testGetDunningReport_Success() throws Exception {
        List<DunningReport> report = new ArrayList<>();
        when(notificationService.getDunningReport()).thenReturn(report);
        ResponseEntity<List<DunningReport>> response = adminController.getDunningReport(1L);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(report, response.getBody());
    }

    @Test
    void testGetPaymentDetailsReport_Success() throws Exception {
        List<PaymentDetails> report = new ArrayList<>();
        when(paymentService.getPaymentDetailsReport()).thenReturn(report);
        ResponseEntity<List<PaymentDetails>> response = adminController.getPaymentDetailsReport(1L);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(report, response.getBody());
    }

    @Test
    void testGetExceptionReport_Success() throws Exception {
        List<ExceptionReport> report = new ArrayList<>();
        when(notificationService.getExceptionReport()).thenReturn(report);
        ResponseEntity<List<ExceptionReport>> response = adminController.getExceptionReport(1L);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(report, response.getBody());
    }

    @Test
    void testGetPerformanceDashboardReport_Success() throws Exception {
        List<PerformanceDashboardReport> report = new ArrayList<>();
        when(notificationService.getPerformanceDashboardReport()).thenReturn(report);
        ResponseEntity<List<PerformanceDashboardReport>> response = adminController.getPerformanceDashboardReport(1L);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(report, response.getBody());
    }


}
