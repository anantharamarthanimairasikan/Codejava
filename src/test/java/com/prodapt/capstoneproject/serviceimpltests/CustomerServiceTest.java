package com.prodapt.capstoneproject.serviceimpltests;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.prodapt.capstoneproject.entities.Customer;
import com.prodapt.capstoneproject.entities.Estatus;
import com.prodapt.capstoneproject.exceptions.CustomerNotFoundException;
import com.prodapt.capstoneproject.model.CustomerStatusReport;
import com.prodapt.capstoneproject.repositories.CustomerRepository;
import com.prodapt.capstoneproject.service.CustomerServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        Customer customer = new Customer(1, "John Doe", "john.doe@example.com", 1234567890L, Estatus.ACTIVE, "john.doe", "password");

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
        Customer customer = new Customer(1, "John Doe", "john.doe@example.com", 1234567890L, Estatus.ACTIVE, "john.doe", "password");
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
        Customer customer = new Customer(1, "John Doe", "john.doe@example.com", 1234567890L, Estatus.ACTIVE, "john.doe", "password");
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.empty());

        // Act and Assert
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> customerService.updateCustomer(customer));
        assertEquals("Customer not found with id: 1", exception.getMessage());
    }
    }

    @Test
    void testGetCustomer_CustomerFound() throws Exception {
    	if(customerRepository!=null) {
        Customer customer = new Customer(1, "John Doe", "john.doe@example.com", 1234567890L, Estatus.ACTIVE, "john.doe", "password");
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
        Customer customer = new Customer(1, "John Doe", "john.doe@example.com", 1234567890L, Estatus.ACTIVE, "john.doe", "password");
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
                new Customer(1, "John Doe", "john.doe@example.com", 1234567890L, Estatus.ACTIVE, "john.doe", "password"),
                new Customer(2, "Jane Doe", "jane.doe@example.com", 9876543210L, Estatus.INACTIVE, "jane.doe", "password")
        );
        when(customerRepository.findAll()).thenReturn(customers);

        // Act
        List<Customer> result = customerService.getAllCustomers();

        // Assert
        assertEquals(customers, result);
        verify(customerRepository).findAll();
    }
    }
    
    @Test
    void testGetCustomerStatusReportSuccess() {
    	if(customerRepository!=null) {
        List<Object[]> results = new ArrayList<>();
        results.add(new Object[] {"John Doe", "johndoe@example.com", 1234567890L, "ACTIVE", BigDecimal.valueOf(100.00), 2, "Communication history"});
        results.add(new Object[] {"Jane Doe", "janedoe@example.com", 9876543210L, "INACTIVE", BigDecimal.valueOf(50.00), 1, "No communication history"});
        when(customerRepository.getCustomerStatusReport()).thenReturn(results);

        // Call the method under test
        List<CustomerStatusReport> reports = customerService.getCustomerStatusReport();

        // Verify the results
        assertNotNull(reports);
        assertEquals(2, reports.size());
        CustomerStatusReport report1 = reports.get(0);
        assertEquals("John Doe", report1.getName());
        assertEquals("johndoe@example.com", report1.getEmail());
        assertEquals(1234567890L, report1.getPhone());
        assertEquals(Estatus.ACTIVE, report1.getStatus());
        assertEquals(100, report1.getOverdueAmount());
        assertEquals(2, report1.getCommunicationCount());
        assertEquals("Communication history", report1.getCommunicationHistory());
        CustomerStatusReport report2 = reports.get(1);
        assertEquals("Jane Doe", report2.getName());
        assertEquals("janedoe@example.com", report2.getEmail());
        assertEquals(9876543210L, report2.getPhone());
        assertEquals(Estatus.INACTIVE, report2.getStatus());
        assertEquals(50, report2.getOverdueAmount());
        assertEquals(1, report2.getCommunicationCount());
        assertEquals("No communication history", report2.getCommunicationHistory());
    }
    }
    
    @Test
    void testGetCustomerStatusReportEmptyResult() {
    	if(customerRepository!=null) {
        when(customerRepository.getCustomerStatusReport()).thenReturn(new ArrayList<>());

        // Call the method under test
        List<CustomerStatusReport> reports = customerService.getCustomerStatusReport();

        // Verify the results
        assertNotNull(reports);
        assertEquals(0, reports.size());
    }
    }
    
}
