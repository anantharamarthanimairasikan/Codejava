package com.prodapt.capstoneproject.serviceimpltests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.prodapt.capstoneproject.entities.Account;
import com.prodapt.capstoneproject.entities.Customer;
import com.prodapt.capstoneproject.entities.Notification;
import com.prodapt.capstoneproject.exceptions.AccountNotFoundException;
import com.prodapt.capstoneproject.repositories.AccountsRepository;
import com.prodapt.capstoneproject.repositories.CustomerRepository;
import com.prodapt.capstoneproject.service.AccountsServiceImpl;
import com.prodapt.capstoneproject.service.NotificationService;

@SpringBootTest
public class AccountServiceTest {
	
	@Mock
	private CustomerRepository customerRepository;
	
	@Mock
    private NotificationService repo;

    @Mock
    private AccountsRepository accountsRepository;

    @InjectMocks
    private AccountsServiceImpl accountsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddAccount_Success() {
        Account account = new Account();
        when(accountsRepository.save(account)).thenReturn(account);

        Account result = accountsService.addAccount(account);

        assertNotNull(result);
        verify(accountsRepository, times(1)).save(account);
    }

    @Test
    public void testUpdateAccount_Success() throws AccountNotFoundException {
        Account account = new Account();
        account.setAccountid(1L);
        when(accountsRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountsRepository.save(account)).thenReturn(account);

        Account result = accountsService.updateAccount(account);

        assertNotNull(result);
        verify(accountsRepository, times(1)).findById(1L);
        verify(accountsRepository, times(1)).save(account);
    }

    @Test
    public void testUpdateAccount_NotFound() {
        Account account = new Account();
        account.setAccountid(1L);
        when(accountsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> {
            accountsService.updateAccount(account);
        });
        verify(accountsRepository, times(1)).findById(1L);
        verify(accountsRepository, times(0)).save(account);
    }

    @Test
    public void testFindAccount_Success() throws AccountNotFoundException {
        Account account = new Account();
        when(accountsRepository.findById(1L)).thenReturn(Optional.of(account));

        Account result = accountsService.findAccount(1L);

        assertNotNull(result);
        verify(accountsRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindAccount_NotFound() {
        when(accountsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> {
            accountsService.findAccount(1L);
        });
        verify(accountsRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteAccount_Success() throws AccountNotFoundException {
        Account account = new Account();
        when(accountsRepository.findById(1L)).thenReturn(Optional.of(account));

        accountsService.deleteAccount(1L);

        verify(accountsRepository, times(1)).findById(1L);
        verify(accountsRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteAccount_NotFound() {
        when(accountsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> {
            accountsService.deleteAccount(1L);
        });
        verify(accountsRepository, times(1)).findById(1L);
        verify(accountsRepository, times(0)).deleteById(1L);
    }

    @Test
    public void testGetAllAccounts() {
        List<Account> accounts = Arrays.asList(new Account(), new Account());
        when(accountsRepository.findAll()).thenReturn(accounts);

        List<Account> result = accountsService.getAllAccounts();

        assertEquals(2, result.size());
        verify(accountsRepository, times(1)).findAll();
    }

    @Test
    public void testFindAccountByCustomerId_Success() throws AccountNotFoundException {
        Account account = new Account();
        when(accountsRepository.findByCustomerId(1)).thenReturn(Optional.of(account));

        Account result = accountsService.findAccountByCustomerId(1);

        assertNotNull(result);
        verify(accountsRepository, times(1)).findByCustomerId(1);
    }

    @Test
    public void testFindAccountByCustomerId_NotFound() {
        when(accountsRepository.findByCustomerId(1)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> {
            accountsService.findAccountByCustomerId(1);
        });
        verify(accountsRepository, times(1)).findByCustomerId(1);
    }
    
    @Test
    public void testSendPaymentReminders_NoCustomers() {
        // Given: no customers
        when(customerRepository.findAll()).thenReturn(new ArrayList<>());

        // When: send payment reminders
        accountsService.sendPaymentReminders();

        // Then: no notifications sent
        verify(repo, never()).addNotification(any(Notification.class));
    }

    @Test
    public void testSendPaymentReminders_CustomerWithPaymentDueInLessThan5Days() {
        // Given: customer with payment due in less than 5 days
        Customer customer = new Customer();
        customer.setId(1);
        Account account = new Account();
        account.setDueDate(LocalDate.now().plusDays(3));
        account.setAmount(100);
        when(customerRepository.findAll()).thenReturn(List.of(customer));
        when(accountsRepository.findById(1L)).thenReturn(Optional.of(account));

        // When: send payment reminders
        accountsService.sendPaymentReminders();

        // Then: notification sent
        verify(repo, times(1)).addNotification(any(Notification.class));
    }

    @Test
    public void testSendPaymentReminders_CustomerWithPaymentDueInMoreThan5DaysButLessThan15Days() {
        // Given: customer with payment due in more than 5 days but less than 15 days
        Customer customer = new Customer();
        customer.setId(1);
        Account account = new Account();
        account.setDueDate(LocalDate.now().plusDays(10));
        account.setAmount(100);
        when(customerRepository.findAll()).thenReturn(List.of(customer));
        when(accountsRepository.findById(1L)).thenReturn(Optional.of(account));

        // When: send payment reminders
        accountsService.sendPaymentReminders();

        // Then: notification sent
        verify(repo, times(1)).addNotification(any(Notification.class));
    }

    @Test
    public void testSendPaymentReminders_CustomerWithPaymentDueInMoreThan15Days() {
        // Given: customer with payment due in more than 15 days
        Customer customer = new Customer();
        customer.setId(1);
        Account account = new Account();
        account.setDueDate(LocalDate.now().plusDays(20));
        account.setAmount(100);
        when(customerRepository.findAll()).thenReturn(List.of(customer));
        when(accountsRepository.findById(1L)).thenReturn(Optional.of(account));

        // When: send payment reminders
        accountsService.sendPaymentReminders();

        // Then: notification sent
        verify(repo, times(1)).addNotification(any(Notification.class));
    }

    @Test
    public void testSendPaymentReminders_ErrorFetchingAccount() {
        // Given: error fetching account
        when(accountsRepository.findById(anyLong())).thenThrow(new RuntimeException("Error fetching account"));

        // When: send payment reminders
        accountsService.sendPaymentReminders();

        // Then: error logged
    }

    @Test
    public void testSendPaymentReminders_CustomerWithNullOrEmptyAccount() {
        // Given: customer with null or empty account
        Customer customer = new Customer();
        customer.setId(1);
        when(customerRepository.findAll()).thenReturn(List.of(customer));
        when(accountsRepository.findById(1L)).thenReturn(Optional.empty());

        // When: send payment reminders
        accountsService.sendPaymentReminders();

        // Then: no notification sent
        verify(repo, never()).addNotification(any(Notification.class));
    }

    @Test
    public void testSendPaymentReminders_CustomerWithAmount0OrNegative() {
        // Given: customer with amount 0 or negative
        Customer customer = new Customer();
        customer.setId(1);
        Account account = new Account();
        account.setAmount(0);
        when(customerRepository.findAll()).thenReturn(List.of(customer));
        when(accountsRepository.findById(1L)).thenReturn(Optional.of(account));
        
        verify(repo, never()).addNotification(any(Notification.class));
    }
    
    @Test
    public void testSendPaymentReminders_CustomerWithDueDateInThePast() {
        // Given: customer with due date in the past
        Customer customer = new Customer();
        customer.setId(1);
        Account account = new Account();
        account.setDueDate(LocalDate.now().minusDays(5));
        account.setAmount(100);
        when(customerRepository.findAll()).thenReturn(List.of(customer));
        when(accountsRepository.findById(1L)).thenReturn(Optional.of(account));

        // When: send payment reminders
        accountsService.sendPaymentReminders();

        // Then: no notification sent
        verify(repo, never()).addNotification(any(Notification.class));
    }

    @Test
    public void testSendPaymentReminders_CustomerWithDueDateToday() {
        // Given: customer with due date today
        Customer customer = new Customer();
        customer.setId(1);
        Account account = new Account();
        account.setDueDate(LocalDate.now());
        account.setAmount(100);
        when(customerRepository.findAll()).thenReturn(List.of(customer));
        when(accountsRepository.findById(1L)).thenReturn(Optional.of(account));

        // When: send payment reminders
        accountsService.sendPaymentReminders();

        // Then: notification sent
        verify(repo, times(1)).addNotification(any(Notification.class));
    }

    @Test
    public void testSendPaymentReminders_ErrorAddingNotification() {
        // Given: error adding notification
        when(repo.addNotification(any(Notification.class))).thenThrow(new RuntimeException("Error adding notification"));

        // When: send payment reminders
        accountsService.sendPaymentReminders();

        // Then: error logged
        
    }

}
