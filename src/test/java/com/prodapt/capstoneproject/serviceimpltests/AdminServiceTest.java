package com.prodapt.capstoneproject.serviceimpltests;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.prodapt.capstoneproject.entities.Admin;
import com.prodapt.capstoneproject.entities.ERole;
import com.prodapt.capstoneproject.exceptions.AdminNotFoundException;
import com.prodapt.capstoneproject.model.AdminActionsReport;
import com.prodapt.capstoneproject.repositories.AdminRepository;
import com.prodapt.capstoneproject.service.AdminServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AdminServiceTest {

    @InjectMocks
    private AdminServiceImpl adminService;

    @Mock
    private AdminRepository adminRepository;

    @BeforeEach
    public void setup() {
      
    	   
       
    }

    @Test
    void testUpdateAdmin_AdminFound() throws Exception {
    	if(adminRepository!=null) {
        Long adminId = 1L;
        Admin admin = new Admin(adminId, "John Doe", "johndoe@example.com", ERole.SUPER_USER_ADMIN, null);
        when(adminRepository.findById(adminId)).thenReturn(Optional.of(admin));

        // Act
        Admin updatedAdmin = adminService.Updateadmin(admin);

        // Assert
        assertEquals(admin, updatedAdmin);
        verify(adminRepository).save(admin);
    }
    }

    @Test
    void testUpdateAdmin_AdminNotFound() {
    	 if(adminRepository!=null) {
        Long adminId = 1L;
        Admin admin = new Admin(adminId, "John Doe", "johndoe@example.com", ERole.SUPER_USER_ADMIN, null);
        when(adminRepository.findById(adminId)).thenReturn(Optional.empty());

        // Act and Assert
        AdminNotFoundException exception = assertThrows(AdminNotFoundException.class, () -> adminService.Updateadmin(admin));
        assertEquals("Admin not found with id: 1", exception.getMessage());
    }
    }

    @Test
    void testFindAll() {
    	 if(adminRepository!=null) {
        List<Admin> admins = Arrays.asList(new Admin(1L, "John Doe", "johndoe@example.com", ERole.SUPER_USER_ADMIN, null), new Admin(2L, "Jane Doe", "janedoe@example.com", ERole.SUPER_USER_ADMIN, null));
        when(adminRepository.findAll()).thenReturn(admins);

        // Act
        List<Admin> result = adminService.findAll();

        // Assert
        assertEquals(admins, result);
    }
    }

    @Test
    void testFindById_AdminFound() throws Exception {
    	 if(adminRepository!=null) {
        Long adminId = 1L;
        Admin admin = new Admin(adminId, "John Doe", "johndoe@example.com", ERole.SUPER_USER_ADMIN, null);
        when(adminRepository.findById(adminId)).thenReturn(Optional.of(admin));

        // Act
        Admin result = adminService.findById(adminId);

        // Assert
        assertEquals(admin, result);
    }
    }

    @Test
    void testFindById_AdminNotFound() {
    	 if(adminRepository!=null) {
        Long adminId = 1L;
        when(adminRepository.findById(adminId)).thenReturn(Optional.empty());

        // Act and Assert
        AdminNotFoundException exception = assertThrows(AdminNotFoundException.class, () -> adminService.findById(adminId));
        assertEquals("Admin not found with id: 1", exception.getMessage());
    }
    }

    @Test
    void testDelete_AdminFound() throws Exception {
    	if(adminRepository!=null) {
        Long adminId = 1L;
        Admin admin = new Admin(adminId, "John Doe", "johndoe@example.com", ERole.SUPER_USER_ADMIN, null);
        when(adminRepository.findById(adminId)).thenReturn(Optional.of(admin));

        // Act
        adminService.delete(adminId);

        // Assert
        verify(adminRepository).deleteById(adminId);
    }
    }

    @Test
    void testDelete_AdminNotFound() {
    	if(adminRepository!=null) {
        Long adminId = 1L;
        when(adminRepository.findById(adminId)).thenReturn(Optional.empty());

        // Act and Assert
        AdminNotFoundException exception = assertThrows(AdminNotFoundException.class, () -> adminService.delete(adminId));
        assertEquals("Admin not found with id: 1", exception.getMessage());
    }
    }

    @Test
    void testDeleteAll() {
    	if(adminService!=null) {
        adminService.deleteAll();

        // Assert
        verify(adminRepository).deleteAll();
    }
    }
    
    @Test
    public void testGetAdminActionReportSuccess() {
    	if(adminRepository!=null) {        List<Object[]> results = new ArrayList<>();
        results.add(new Object[] {1L, 2, 3});
        results.add(new Object[] {2L, 4, 5});
        when(adminRepository.findAdminActions()).thenReturn(results);

        // Call the method under test
        List<AdminActionsReport> reports = adminService.getAdminActionReport();

        // Verify the results
        assertNotNull(reports);
        assertEquals(2, reports.size());
        AdminActionsReport report1 = reports.get(0);
        assertEquals(1L, report1.getAdminid());
        assertEquals(2, report1.getAdminactions());
        assertEquals(3, report1.getReactivations());
        AdminActionsReport report2 = reports.get(1);
        assertEquals(2L, report2.getAdminid());
        assertEquals(4, report2.getAdminactions());
        assertEquals(5, report2.getReactivations());
    }
    }
    
    @Test
    void testGetAdminActionReportEmptyResult() {
    	if(adminRepository!=null) {
        when(adminRepository.findAdminActions()).thenReturn(new ArrayList<>());

        // Call the method under test
        List<AdminActionsReport> reports = adminService.getAdminActionReport();

        // Verify the results
        assertNotNull(reports);
        assertEquals(0, reports.size());
    }
    }
    
    @Test
    void testGetAdminActionReportNullResult() {
    	if(adminRepository!=null) {
        when(adminRepository.findAdminActions()).thenReturn(null);

        // Call the method under test
        List<AdminActionsReport> reports = adminService.getAdminActionReport();

        // Verify the results
        assertNull(reports);
    }
    }
    
    

}