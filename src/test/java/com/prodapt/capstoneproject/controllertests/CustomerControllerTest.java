package com.prodapt.capstoneproject.controllertests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.prodapt.capstoneproject.controller.CustomerController;
import com.prodapt.capstoneproject.entities.Customer;
import com.prodapt.capstoneproject.entities.Estatus;
import com.prodapt.capstoneproject.exceptions.CustomerNotFoundException;
import com.prodapt.capstoneproject.service.CustomerService;

public class CustomerControllerTest {

	@InjectMocks
    private CustomerController customerController;
 
    @Mock
    private CustomerService customerService;
 
    private Customer customer;
    private List<Customer> customerList;
 
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer();
        customer.setId(1);
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPhone(1234567890L);
        customer.setStatus(Estatus.ACTIVE);  // Assuming Estatus enum exists
        customer.setUsername("johndoe");
        customer.setPassword("password");
 
        customerList = Arrays.asList(customer);
    }
 
    @Test
    public void testAddCustomer_Success() {
        when(customerService.addCustomer(customer)).thenReturn(customer);
        ResponseEntity<Customer> response = customerController.addCustomer(customer);
 
        verify(customerService).addCustomer(customer);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }
 
    @Test
    public void testGetAllCustomers_Success() {
        when(customerService.getAllCustomers()).thenReturn(customerList);
        ResponseEntity<Iterable<Customer>> response = customerController.getAllCustomers();
 
        verify(customerService).getAllCustomers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerList, response.getBody());
    }
 
    @Test
    public void testGetCustomer_Success() throws CustomerNotFoundException {
        Integer id = 1;
        when(customerService.findCustomer(id)).thenReturn(customer);
        ResponseEntity<Customer> response = customerController.getCustomer(id);
 
        verify(customerService).findCustomer(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }
 
    @Test
    public void testUpdateCustomer_Success() throws CustomerNotFoundException {
        when(customerService.updateCustomer(customer)).thenReturn(customer);
        ResponseEntity<Customer> response = customerController.updateCustomer(customer);
 
        verify(customerService).updateCustomer(customer);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }
 
    @Test
    public void testDeleteCustomer_Success() throws CustomerNotFoundException {
        Integer id = 1;
        doNothing().when(customerService).deleteCustomer(id);
        ResponseEntity<Void> response = customerController.deleteCustomer(id);
 
        verify(customerService).deleteCustomer(id);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
 
    @Test
    public void testGetCustomer_NotFound() throws CustomerNotFoundException {
        Integer id = 2;
        when(customerService.findCustomer(id)).thenThrow(new CustomerNotFoundException("Customer not found"));
 
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> {
            customerController.getCustomer(id);
        });
 
        assertEquals("Customer not found", exception.getMessage());
        ResponseEntity<String> response = customerController.handleCustomerNotFoundException(exception);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Customer not found", response.getBody());
    }
 
    @Test
    public void testUpdateCustomer_NotFound() throws CustomerNotFoundException {
        when(customerService.updateCustomer(customer)).thenThrow(new CustomerNotFoundException("Customer not found"));
 
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> {
            customerController.updateCustomer(customer);
        });
 
        assertEquals("Customer not found", exception.getMessage());
        ResponseEntity<String> response = customerController.handleCustomerNotFoundException(exception);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Customer not found", response.getBody());
    }
 
    @Test
    public void testDeleteCustomer_NotFound() throws CustomerNotFoundException {
        Integer id = 2;
        doThrow(new CustomerNotFoundException("Customer not found")).when(customerService).deleteCustomer(id);
 
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> {
            customerController.deleteCustomer(id);
        });
 
        assertEquals("Customer not found", exception.getMessage());
        ResponseEntity<String> response = customerController.handleCustomerNotFoundException(exception);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Customer not found", response.getBody());
    }
 
    @Test
    public void testAddCustomer_NullCustomer() {
        when(customerService.addCustomer(null)).thenReturn(null);
        ResponseEntity<Customer> response = customerController.addCustomer(null);
 
        verify(customerService).addCustomer(null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }
 
    @Test
    public void testUpdateCustomer_NullCustomer() throws CustomerNotFoundException {
        when(customerService.updateCustomer(null)).thenReturn(null);
        ResponseEntity<Customer> response = customerController.updateCustomer(null);
 
        verify(customerService).updateCustomer(null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }
 
    @Test
    public void testHandleCustomerNotFoundException() {
        CustomerNotFoundException exception = new CustomerNotFoundException("Customer not found");
        ResponseEntity<String> response = customerController.handleCustomerNotFoundException(exception);
 
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Customer not found", response.getBody());
    }
    
    @Test
    void testGetCustomerbyusername_Success() throws CustomerNotFoundException {
        // Mock data
        String username = "testuser";
        Customer expectedCustomer = new Customer();
        expectedCustomer.setId(1);
        expectedCustomer.setUsername(username);

        // Mock CustomerServic
        when(customerService.findCustomerbyUsername(username)).thenReturn(expectedCustomer);

     
        ResponseEntity<Customer> response = customerController.getCustomerByUsername(username);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCustomer, response.getBody());
    }
    
    @Test
    void testGetCustomerbyusername_CustomerNotFoundException() throws CustomerNotFoundException {
        // Mock data
        String username = "nonexistentuser";

        // Mock CustomerService to throw CustomerNotFoundException
        when(customerService.findCustomerbyUsername(username)).thenThrow(new CustomerNotFoundException("Customer not found"));

        ResponseEntity<Customer> response = customerController.getCustomerByUsername(username);

        // Verify the response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }



}
