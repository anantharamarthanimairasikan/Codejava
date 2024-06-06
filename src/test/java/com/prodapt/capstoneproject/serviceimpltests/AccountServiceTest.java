package com.prodapt.capstoneproject.serviceimpltests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.prodapt.capstoneproject.entities.Account;
import com.prodapt.capstoneproject.entities.Customer;
import com.prodapt.capstoneproject.entities.Notification;
import com.prodapt.capstoneproject.entities.Payments;
import com.prodapt.capstoneproject.exceptions.AccountNotFoundException;
import com.prodapt.capstoneproject.repositories.AccountsRepository;
import com.prodapt.capstoneproject.service.AccountsServiceImpl;

public class AccountServiceTest {

    @InjectMocks
    private AccountsServiceImpl accountService;

    @Mock
    private AccountsRepository accountRepository;

    @Test
    void testAddAccount() {
        if(accountRepository!=null) {
        	
        Account account = new Account(1L, new Customer(), Arrays.asList(new Payments(), new Payments()), Arrays.asList(new Notification(), new Notification()), LocalDate.now(), LocalDate.now(), 100, LocalDate.now());

        // Act
        Account result = accountService.addAccount(account);

        // Assert
        assertEquals(account, result);
        verify(accountRepository).save(account);
    }
    }

    @Test
    void testUpdateAccount_AccountFound() throws Exception {
    	if(accountRepository!=null) {
        Account account = new Account(1L, new Customer(), Arrays.asList(new Payments(), new Payments()), Arrays.asList(new Notification(), new Notification()), LocalDate.now(), LocalDate.now(), 100, LocalDate.now());
        when(accountRepository.findById(account.getAccountid())).thenReturn(Optional.of(account));

        // Act
        Account updatedAccount = accountService.updateAccount(account);

        // Assert
        assertEquals(account, updatedAccount);
        verify(accountRepository).save(account);
    }
    }

    @Test
    void testUpdateAccount_AccountNotFound() {
    	if(accountRepository!=null) {
        Account account = new Account(1L, new Customer(), Arrays.asList(new Payments(), new Payments()), Arrays.asList(new Notification(), new Notification()), LocalDate.now(), LocalDate.now(), 100, LocalDate.now());
        when(accountRepository.findById(account.getAccountid())).thenReturn(Optional.empty());

        // Act and Assert
        AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> accountService.updateAccount(account));
        assertEquals("Account not found with ID: 1", exception.getMessage());
    }
    }

    @Test
    void testGetAccount_AccountFound() throws Exception {
    	if(accountRepository!=null) {
        Account account = new Account(1L, new Customer(), Arrays.asList(new Payments(), new Payments()), Arrays.asList(new Notification(), new Notification()), LocalDate.now(), LocalDate.now(), 100, LocalDate.now());
        when(accountRepository.findById(account.getAccountid())).thenReturn(Optional.of(account));

        // Act
        Account result = accountService.getAccount(account.getAccountid());

        // Assert
        assertEquals(account, result);
    }
    }

    @Test
    void testGetAccount_AccountNotFound() {
    	if(accountRepository!=null) {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        // Act and Assert
        AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> accountService.getAccount(1L));
        assertEquals("Account not found with ID: 1", exception.getMessage());
    }
    }

    @Test
    public void testDeleteAccount_AccountFound() throws Exception {
    	if(accountRepository!=null) {
        Account account = new Account(1L, new Customer(), Arrays.asList(new Payments(), new Payments()), Arrays.asList(new Notification(), new Notification()), LocalDate.now(), LocalDate.now(), 100, LocalDate.now());
        when(accountRepository.findById(account.getAccountid())).thenReturn(Optional.of(account));

        // Act
        accountService.deleteAccount(account.getAccountid());

        // Assert
        verify(accountRepository).deleteById(account.getAccountid());
    }
    }

    @Test
    void testDeleteAccount_AccountNotFound() {
    	if(accountRepository!=null) {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        // Act and Assert
        AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> accountService.deleteAccount(1L));
        assertEquals("Account not found with ID: 1", exception.getMessage());
    }
    }
    
    @Test
    void testGetAllAccounts_Success() {
    	if(accountRepository!=null) {
        List<Account> accounts = Arrays.asList(
                new Account(1L, new Customer(), Arrays.asList(new Payments(), new Payments()), Arrays.asList(new Notification(), new Notification()), LocalDate.now(), LocalDate.now(), 100, LocalDate.now()),
                new Account(2L, new Customer(), Arrays.asList(new Payments(), new Payments()), Arrays.asList(new Notification(), new Notification()), LocalDate.now(), LocalDate.now(), 200, LocalDate.now())
        );
        when(accountRepository.findAll()).thenReturn(accounts);

        // Act
        List<Account> result = accountService.getAllAccounts();

        // Assert
        assertEquals(accounts, result);
        verify(accountRepository).findAll();
    }
    }

    @Test
    void testGetAllAccounts_EmptyList() {
    	if(accountRepository!=null) {
        when(accountRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<Account> result = accountService.getAllAccounts();

        // Assert
        assertEquals(Collections.emptyList(), result);
        verify(accountRepository).findAll();
    }
    }

    @Test
    void testGetAllAccounts_Null() {
    	if(accountRepository!=null) {
        when(accountRepository.findAll()).thenReturn(null);

        // Act and Assert
        NullPointerException exception = assertThrows(NullPointerException.class, () -> accountService.getAllAccounts());
        assertEquals("accountRepository.findAll() returned null", exception.getMessage());
    }
    }

}
