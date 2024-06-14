package com.prodapt.capstoneproject.controllertests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.prodapt.capstoneproject.controller.AdminController;
import com.prodapt.capstoneproject.entities.Account;
import com.prodapt.capstoneproject.entities.Admin;
import com.prodapt.capstoneproject.entities.Customer;
import com.prodapt.capstoneproject.entities.ERole;
import com.prodapt.capstoneproject.entities.Notification;
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

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private PasswordEncoder encoder;

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
	void testUpdateAdmin_Success() throws AdminNotFoundException {
		Admin admin = new Admin();
		admin.setAdminid(1L);
		admin.setUsername("adminuser");
		admin.setPassword("newpassword");
		admin.setRole(ERole.SUPER_USER_ADMIN);

		Admin updatedAdmin = new Admin();
		updatedAdmin.setAdminid(1L);
		updatedAdmin.setUsername("adminuser");
		updatedAdmin.setPassword("encodedpassword");
		updatedAdmin.setRole(ERole.SUPER_USER_ADMIN);

		when(adminService.updateAdmin(any(Admin.class))).thenReturn(updatedAdmin);
		when(encoder.encode(any(String.class))).thenReturn("encodedpassword");

		ResponseEntity<Admin> response = adminController.updateAdmin(admin);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(updatedAdmin, response.getBody());

		// Verify user details are saved
		verify(userRepository).save(any(UserEntity.class));
	}

	@Test
	void testUpdateAdmin_AdminNotFoundException() throws AdminNotFoundException {
		Admin admin = new Admin();
		admin.setAdminid(1L);
		admin.setUsername("adminuser");
		admin.setPassword("newpassword");
		admin.setRole(ERole.SUPER_USER_ADMIN);

		when(adminService.updateAdmin(any(Admin.class))).thenThrow(new AdminNotFoundException("Admin not found"));

		ResponseEntity<Admin> response = adminController.updateAdmin(admin);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		// Verify that userRepository.save() is never called in case of exception
		verify(userRepository, never()).save(any(UserEntity.class));
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

	@Test
	void testGetAdminByUsername_Success() throws AdminNotFoundException {
		// Mock data
		String username = "testadmin";
		Admin expectedAdmin = new Admin();
		expectedAdmin.setUsername(username);

		// Mock AdminService
		when(adminService.findByUsername(username)).thenReturn(expectedAdmin);

		// Perform the method call
		ResponseEntity<Admin> response = adminController.getAdminByUsername(username);

		// Verify the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedAdmin, response.getBody());
	}

	@Test
	void testGetAdminByUsername_AdminNotFoundException() throws AdminNotFoundException {
		// Mock data
		String username = "nonexistentadmin";

		// Mock AdminService to throw AdminNotFoundException
		when(adminService.findByUsername(anyString())).thenThrow(new AdminNotFoundException("Admin not found"));

		// Perform the method call
		ResponseEntity<Admin> response = adminController.getAdminByUsername(username);

		// Verify the response
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	

	@Test
    void testSendCustomernotification_Success() throws AccountNotFoundException {
        // Mock data
        Customer customer1 = new Customer();
        customer1.setId(1);
        Customer customer2 = new Customer();
        customer2.setId(2);

        UsersList users = new UsersList();
        users.setCustomers(Arrays.asList(customer1, customer2));

        Account account1 = new Account();
        Account account2 = new Account();

        // Mocking accountService to return accounts
        when(accountService.findAccountByCustomerId(1)).thenReturn(account1);
        when(accountService.findAccountByCustomerId(2)).thenReturn(account2);

        // Perform the method call
        ResponseEntity<String> response = adminController.sendCustomernotification(users);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Notification sent to all customers", response.getBody());

        // Verify interactions
        verify(accountService).findAccountByCustomerId(1);
        verify(accountService).findAccountByCustomerId(2);
        verify(notificationService, times(2)).addNotification(any(Notification.class));
    }

    
    @Test
    void testSendCustomernotification_AccountNotFoundException() throws AccountNotFoundException {
        // Mock data
        Customer customer1 = new Customer();
        customer1.setId(1);
        Customer customer2 = new Customer();
        customer2.setId(2);

        UsersList users = new UsersList();
        users.setCustomers(Arrays.asList(customer1, customer2));

        // Mocking accountService to throw AccountNotFoundException
        when(accountService.findAccountByCustomerId(anyInt())).thenThrow(new AccountNotFoundException("Account not found"));

        // Perform the method call
        ResponseEntity<String> response = adminController.sendCustomernotification(users);

        // Verify the response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Account not found", response.getBody());

        // Verify interactions
        verify(accountService, times(1)).findAccountByCustomerId(anyInt());
        verify(notificationService, never()).addNotification(any(Notification.class));
    }

}
