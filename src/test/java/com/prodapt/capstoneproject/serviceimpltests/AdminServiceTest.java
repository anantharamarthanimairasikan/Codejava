package com.prodapt.capstoneproject.serviceimpltests;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import com.prodapt.capstoneproject.entities.Admin;
import com.prodapt.capstoneproject.exceptions.AdminNotFoundException;
import com.prodapt.capstoneproject.model.AdminActionsReport;
import com.prodapt.capstoneproject.repositories.AdminRepository;
import com.prodapt.capstoneproject.service.AdminServiceImpl;

@SpringBootTest
public class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateAdmin_Success() throws AdminNotFoundException {
        Admin admin = new Admin();
        admin.setAdminid(1L);
        when(adminRepository.findById(1L)).thenReturn(Optional.of(admin));
        when(adminRepository.save(admin)).thenReturn(admin);
        
        Admin result = adminService.updateAdmin(admin);
        
        assertNotNull(result);
        verify(adminRepository, times(1)).save(admin);
    }

    @Test
    void testUpdateAdmin_AdminNotFound() {
        Admin admin = new Admin();
        admin.setAdminid(1L);
        when(adminRepository.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(AdminNotFoundException.class, () -> {
            adminService.updateAdmin(admin);
        });
    }

    @Test
    void testFindAll_WithAdmins() {
        List<Admin> admins = Arrays.asList(new Admin(), new Admin());
        when(adminRepository.findAll()).thenReturn(admins);
        
        List<Admin> result = adminService.findAll();
        
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testFindAll_NoAdmins() {
        when(adminRepository.findAll()).thenReturn(Collections.emptyList());
        
        List<Admin> result = adminService.findAll();
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindById_Success() throws AdminNotFoundException {
        Admin admin = new Admin();
        admin.setAdminid(1L);
        when(adminRepository.findById(1L)).thenReturn(Optional.of(admin));
        
        Admin result = adminService.findById(1L);
        
        assertNotNull(result);
        assertEquals(admin, result);
    }

    @Test
    void testFindById_AdminNotFound() {
        when(adminRepository.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(AdminNotFoundException.class, () -> {
            adminService.findById(1L);
        });
    }

    @Test
    void testDelete_Success() throws AdminNotFoundException {
        Admin admin = new Admin();
        admin.setAdminid(1L);
        when(adminRepository.findById(1L)).thenReturn(Optional.of(admin));
        
        adminService.delete(1L);
        
        verify(adminRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete_AdminNotFound() {
        when(adminRepository.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(AdminNotFoundException.class, () -> {
            adminService.delete(1L);
        });
    }

    @Test
    void testDeleteAll() {
        adminService.deleteAll();
        
        verify(adminRepository, times(1)).deleteAll();
    }

    @Test
    void testGetAdminActionReportSuccess() {
        // Arrange
        List<Object[]> mockResults = new ArrayList<>();
        mockResults.add(new Object[]{1L, 10L, 5L});
        when(adminRepository.findAdminActions()).thenReturn(mockResults);

        // Act
        List<AdminActionsReport> reports = adminService.getAdminActionReport();

        // Assert
        assertNotNull(reports);
        assertEquals(1, reports.size());
        AdminActionsReport report = reports.get(0);
        assertEquals(1L, report.getAdminid());
        assertEquals(10L, report.getAdminactions());
        assertEquals(5L, report.getReactivations());
    }

    @Test
    void testGetAdminActionReportFailure() {
        // Arrange
        when(adminRepository.findAdminActions()).thenReturn(new ArrayList<>());

        // Act
        List<AdminActionsReport> reports = adminService.getAdminActionReport();

        // Assert
        assertNotNull(reports);
        assertTrue(reports.isEmpty());
    }
    
    @Test
    void testFindByUsername_Success() throws AdminNotFoundException {
        // Mock data
        String username = "testadmin";
        Admin admin = new Admin();
        admin.setUsername(username);

        // Mock AdminRepository
        when(adminRepository.findByUsername(username)).thenReturn(Optional.of(admin));

        // Perform the method call
        Admin result = adminService.findByUsername(username);

        // Verify the result
        assertEquals(admin, result);
    }

    @Test
    void testFindByUsername_AdminNotFoundException() {
        // Mock data
        String username = "nonexistentadmin";

        // Mock AdminRepository to return empty optional
        when(adminRepository.findByUsername(username)).thenReturn(Optional.empty());


        assertThrows(AdminNotFoundException.class, () -> adminService.findByUsername(username));
    }
}
