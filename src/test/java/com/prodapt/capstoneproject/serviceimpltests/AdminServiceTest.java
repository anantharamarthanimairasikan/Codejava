package com.prodapt.capstoneproject.serviceimpltests;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.prodapt.capstoneproject.entities.Admin;
import com.prodapt.capstoneproject.entities.ERole;
import com.prodapt.capstoneproject.exceptions.AdminNotFoundException;
import com.prodapt.capstoneproject.repositories.AdminRepository;
import com.prodapt.capstoneproject.service.AdminServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        Admin admin = new Admin(adminId, "John Doe", "johndoe@example.com", ERole.Super_User_Admin, null);
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
        Admin admin = new Admin(adminId, "John Doe", "johndoe@example.com", ERole.Super_User_Admin, null);
        when(adminRepository.findById(adminId)).thenReturn(Optional.empty());

        // Act and Assert
        AdminNotFoundException exception = assertThrows(AdminNotFoundException.class, () -> adminService.Updateadmin(admin));
        assertEquals("Admin not found with id: 1", exception.getMessage());
    }
    }

    @Test
    void testFindAll() {
    	 if(adminRepository!=null) {
        List<Admin> admins = Arrays.asList(new Admin(1L, "John Doe", "johndoe@example.com", ERole.Super_User_Admin, null), new Admin(2L, "Jane Doe", "janedoe@example.com", ERole.Super_User_Admin, null));
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
        Admin admin = new Admin(adminId, "John Doe", "johndoe@example.com", ERole.Super_User_Admin, null);
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
        Admin admin = new Admin(adminId, "John Doe", "johndoe@example.com", ERole.Super_User_Admin, null);
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

}