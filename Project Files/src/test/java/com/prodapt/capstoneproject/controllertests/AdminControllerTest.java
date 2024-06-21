package com.prodapt.capstoneproject.controllertests;

import com.prodapt.capstoneproject.controller.AdminController;
import com.prodapt.capstoneproject.entities.*;
import com.prodapt.capstoneproject.exceptions.AdminNotFoundException;
import com.prodapt.capstoneproject.model.*;
import com.prodapt.capstoneproject.security.service.UserEntityService;
import com.prodapt.capstoneproject.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private AdminService adminService;

    @Mock
    private NotificationService notificationService;

    @Mock
    private AccountService accountService;

    @Mock
    private CustomerService customerService;

    @Mock
    private PaymentService paymentService;

    @Mock
    private ReportService reportService;

    @Mock
    private UserEntityService userEntityService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private Admin admin;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        admin = new Admin();
        admin.setAdminid(1L);
        admin.setUsername("adminuser");
        admin.setPassword("password");
    }

    @Test
    public void testUpdateAdmin_Success() throws Exception {
        when(adminService.updateAdmin(any(Admin.class))).thenReturn(admin);
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");

        ResponseEntity<Admin> response = adminController.updateAdmin(admin);

        verify(adminService).updateAdmin(admin);
        verify(userEntityService).addUserEntity(any(UserEntity.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("adminuser", response.getBody().getUsername());
    }

    @Test
    public void testUpdateAdmin_AdminNotFound() throws Exception {
        when(adminService.updateAdmin(any(Admin.class))).thenThrow(new AdminNotFoundException("Admin not found"));

        ResponseEntity<Admin> response = adminController.updateAdmin(admin);

        verify(adminService).updateAdmin(admin);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testFindAllAdmins_Success() {
        List<Admin> adminList = Arrays.asList(admin);
        when(adminService.findAll()).thenReturn(adminList);

        ResponseEntity<List<Admin>> response = adminController.findAllAdmins();

        verify(adminService).findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testFindAdminById_Success() throws Exception {
        when(adminService.findById(any(Long.class))).thenReturn(admin);

        ResponseEntity<Admin> response = adminController.findAdminById(1L);

        verify(adminService).findById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("adminuser", response.getBody().getUsername());
    }

    @Test
    public void testFindAdminById_AdminNotFound() throws Exception {
        when(adminService.findById(any(Long.class))).thenThrow(new AdminNotFoundException("Admin not found"));

        ResponseEntity<Admin> response = adminController.findAdminById(1L);

        verify(adminService).findById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteAdminById_Success() throws Exception {
        when(adminService.findById(any(Long.class))).thenReturn(admin);

        ResponseEntity<Void> response = adminController.deleteAdminById(1L);

        verify(adminService).findById(1L);
        verify(userEntityService).deletebyusername("adminuser");
        verify(adminService).delete(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteAdminById_AdminNotFound() throws Exception {
        when(adminService.findById(any(Long.class))).thenThrow(new AdminNotFoundException("Admin not found"));

        ResponseEntity<Void> response = adminController.deleteAdminById(1L);

        verify(adminService).findById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteAllAdmins_Success() {
        ResponseEntity<Void> response = adminController.deleteAllAdmins();

        verify(adminService).deleteAll();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
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
	void testGetPerformanceDashboardReport_Success() throws Exception {
		List<PerformanceDashboardReport> report = new ArrayList<>();
		when(notificationService.getPerformanceDashboardReport()).thenReturn(report);
		ResponseEntity<List<PerformanceDashboardReport>> response = adminController.getPerformanceDashboardReport(1L);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(report, response.getBody());
	}

    @Test
    public void testGetAdminByUsername_Success() throws Exception {
        when(adminService.findByUsername(any(String.class))).thenReturn(admin);

        ResponseEntity<Admin> response = adminController.getAdminByUsername("adminuser");

        verify(adminService).findByUsername("adminuser");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("adminuser", response.getBody().getUsername());
    }

    @Test
    public void testGetAdminByUsername_AdminNotFound() throws Exception {
        when(adminService.findByUsername(any(String.class))).thenThrow(new AdminNotFoundException("Admin not found"));

        ResponseEntity<Admin> response = adminController.getAdminByUsername("adminuser");

        verify(adminService).findByUsername("adminuser");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
