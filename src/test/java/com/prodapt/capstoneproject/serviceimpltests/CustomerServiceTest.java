package com.prodapt.capstoneproject.serviceimpltests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import com.prodapt.capstoneproject.entities.Customer;
import com.prodapt.capstoneproject.exceptions.CustomerNotFoundException;
import com.prodapt.capstoneproject.model.CustomerStatusReport;
import com.prodapt.capstoneproject.repositories.CustomerRepository;
import com.prodapt.capstoneproject.service.CustomerServiceImpl;

@SpringBootTest
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddCustomer_Success() {
        Customer customer = new Customer();
        when(customerRepository.save(customer)).thenReturn(customer);
        
        Customer result = customerService.addCustomer(customer);
        
        assertNotNull(result);
        verify(customerRepository, times(1)).save(customer);
    }

  

    @Test
    void testUpdateCustomer_Success() throws CustomerNotFoundException {
        Customer customer = new Customer();
        customer.setId(1);
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);
        
        Customer result = customerService.updateCustomer(customer);
        
        assertNotNull(result);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testUpdateCustomer_CustomerNotFound() {
        Customer customer = new Customer();
        customer.setId(1);
        when(customerRepository.findById(1)).thenReturn(Optional.empty());
        
        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.updateCustomer(customer);
        });
    }

    @Test
    void testFindCustomer_Success() throws CustomerNotFoundException {
        Customer customer = new Customer();
        customer.setId(1);
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        
        Customer result = customerService.findCustomer(1);
        
        assertNotNull(result);
        assertEquals(customer, result);
    }

    @Test
    public void testFindCustomer_CustomerNotFound() {
        when(customerRepository.findById(1)).thenReturn(Optional.empty());
        
        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.findCustomer(1);
        });
    }

    @Test
    public void testDeleteCustomer_Success() throws CustomerNotFoundException {
        Customer customer = new Customer();
        customer.setId(1);
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        
        customerService.deleteCustomer(1);
        
        verify(customerRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteCustomer_CustomerNotFound() {
        when(customerRepository.findById(1)).thenReturn(Optional.empty());
        
        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.deleteCustomer(1);
        });
    }

    @Test
    public void testGetAllCustomers_WithCustomers() {
        List<Customer> customers = Arrays.asList(new Customer(), new Customer());
        when(customerRepository.findAll()).thenReturn(customers);
        
        List<Customer> result = customerService.getAllCustomers();
        
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetAllCustomers_NoCustomers() {
        when(customerRepository.findAll()).thenReturn(Collections.emptyList());
        
        List<Customer> result = customerService.getAllCustomers();
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetCustomerStatusReport_WithData() {
        List<Object[]> mockResults = Arrays.asList(
            new Object[]{"John", "john@example.com", 1234567890L, "ACTIVE", new BigDecimal(100), 3, "Call, Email"},
            new Object[]{"Jane", "jane@example.com", 9876543210L, "INACTIVE", new BigDecimal(200), 1, "Email"}
        );
        when(customerRepository.getCustomerStatusReport()).thenReturn(mockResults);
        
        List<CustomerStatusReport> reports = customerService.getCustomerStatusReport();
        
        assertNotNull(reports);
        assertEquals(2, reports.size());
        
        assertEquals("John", reports.get(0).getName());
        assertEquals("ACTIVE", reports.get(0).getStatus().name());
        assertEquals(100, reports.get(0).getOverdueAmount());
        
        assertEquals("Jane", reports.get(1).getName());
        assertEquals("INACTIVE", reports.get(1).getStatus().name());
        assertEquals(200, reports.get(1).getOverdueAmount());
    }

    @Test
    public void testGetCustomerStatusReport_NoData() {
        when(customerRepository.getCustomerStatusReport()).thenReturn(Collections.emptyList());
        
        List<CustomerStatusReport> reports = customerService.getCustomerStatusReport();
        
        assertNotNull(reports);
        assertTrue(reports.isEmpty());
    }
    
    @Test
    void testFindCustomerbyUsername_Success() {
        // Mock data
        String username = "existinguser";
        Customer customer = new Customer();
        customer.setUsername(username);

        // Mock the customer repository to return the customer when findByUsername is called
        when(customerRepository.findByUsername(username)).thenReturn(Optional.of(customer));

        // Call the method
        try {
            Customer result = customerService.findCustomerbyUsername(username);

            // Verify the result
            assertNotNull(result);
            assertEquals(username, result.getUsername());
        } catch (CustomerNotFoundException e) {
            fail("Unexpected CustomerNotFoundException");
        }
    }

    @Test
    void testFindCustomerbyUsername_CustomerNotFoundException() {
        // Mock data
        String username = "nonexistentuser";

        // Mock the customer repository to return an empty optional when findByUsername is called
        when(customerRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Call the method and expect CustomerNotFoundException
        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.findCustomerbyUsername(username);
        });
    }
    
    
}
