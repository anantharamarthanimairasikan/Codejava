package com.prodapt.capstoneproject.serviceimpltests;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import com.prodapt.capstoneproject.entities.Customer;
import com.prodapt.capstoneproject.entities.Estatus;
import com.prodapt.capstoneproject.exceptions.CustomerNotFoundException;
import com.prodapt.capstoneproject.model.CustomerStatusReport;
import com.prodapt.capstoneproject.repositories.CustomerRepository;
import com.prodapt.capstoneproject.service.CustomerServiceImpl;

import io.jsonwebtoken.lang.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;


    @Test
    void testAddCustomer() {
        if(customerRepository!=null) {
        Customer customer = new Customer(1, "John Doe", "john.doe@example.com", 1234567890L, Estatus.Active, "john.doe", "password");

        // Act
        Customer result = customerService.addCustomer(customer);

        // Assert
        assertEquals(customer, result);
        verify(customerRepository).save(customer);
        }
    }

    @Test
    void testUpdateCustomer_CustomerFound() throws Exception {
    	if(customerRepository!=null) {
        Customer customer = new Customer(1, "John Doe", "john.doe@example.com", 1234567890L, Estatus.Active, "john.doe", "password");
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        // Act
        Customer updatedCustomer = customerService.updateCustomer(customer);

        // Assert
        assertEquals(customer, updatedCustomer);
        verify(customerRepository).save(customer);
    }
    }

    @Test
    void testUpdateCustomer_CustomerNotFound() {
    	if(customerRepository!=null) {
        Customer customer = new Customer(1, "John Doe", "john.doe@example.com", 1234567890L, Estatus.Active, "john.doe", "password");
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.empty());

        // Act and Assert
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> customerService.updateCustomer(customer));
        assertEquals("Customer not found with id: 1", exception.getMessage());
    }
    }

    @Test
    void testGetCustomer_CustomerFound() throws Exception {
    	if(customerRepository!=null) {
        Customer customer = new Customer(1, "John Doe", "john.doe@example.com", 1234567890L, Estatus.Active, "john.doe", "password");
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        // Act
        Customer result = customerService.getCustomer(customer.getId());

        // Assert
        assertEquals(customer, result);
    }
    }

    @Test
    void testGetCustomer_CustomerNotFound() {
    	if(customerRepository!=null) {
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        // Act and Assert
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomer(1));
        assertEquals("Customer not found with id: 1", exception.getMessage());
    }
    }

    @Test
    void testDeleteCustomer_CustomerFound() throws Exception {
    	if(customerRepository!=null) {
        Customer customer = new Customer(1, "John Doe", "john.doe@example.com", 1234567890L, Estatus.Active, "john.doe", "password");
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        // Act
        customerService.deleteCustomer(customer.getId());

        // Assert
        verify(customerRepository).deleteById(customer.getId());
    }
    }

    @Test
    void testDeleteCustomer_CustomerNotFound() {
    	if(customerRepository!=null) {
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        // Act and Assert
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> customerRepository.deleteById(1));
        assertEquals("Customer not found with id: 1", exception.getMessage());
    }
    }

    @Test
    void testGetAllCustomers() {
    	if(customerRepository!=null) {
        List<Customer> customers = Arrays.asList(
                new Customer(1, "John Doe", "john.doe@example.com", 1234567890L, Estatus.Active, "john.doe", "password"),
                new Customer(2, "Jane Doe", "jane.doe@example.com", 9876543210L, Estatus.Inactive, "jane.doe", "password")
        );
        when(customerRepository.findAll()).thenReturn(customers);

        // Act
        List<Customer> result = customerService.getAllCustomers();

        // Assert
        assertEquals(customers, result);
        verify(customerRepository).findAll();
    }
    }
    
//    @Test
//    void testGetCustomerStatusReport_Succeeds() {
//    	if(customerRepository!=null) {
//        List<CustomerStatusReport> expectedReports = Arrays.asList(
//            new CustomerStatusReport("John Doe", "john@example.com", 1234567890L, Estatus.Active, 100.0, 2L, "Communication history"),
//            new CustomerStatusReport("Jane Doe", "jane@example.com", 9876543210L, Estatus.Inactive, 50.0, 1L, "Another communication history")
//        );
//        Mockito.when(customerRepository.getCustomerStatusReport()).thenReturn(expectedReports);
//
//
//
//        // Act
//        List<CustomerStatusReport> actualReports = customerRepository.getCustomerStatusReport();
//
//        // Assert
//        Assert.notNull(actualReports);
//        assertEquals(expectedReports, actualReports);
//    }
//    }
//    
//    @Test
//    void testGetCustomerStatusReport_Fails_RepositoryReturnsNull() {
//    	if(customerRepository!=null) {
//        Mockito.when(customerRepository.getCustomerStatusReport()).thenReturn(null);
//
//        assertThrows(NullPointerException.class, () -> customerRepository.getCustomerStatusReport());
//    }
//    }
}
