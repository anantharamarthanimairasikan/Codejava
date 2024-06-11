package com.prodapt.capstoneproject.serviceimpltests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
import com.prodapt.capstoneproject.exceptions.AccountNotFoundException;
import com.prodapt.capstoneproject.repositories.AccountsRepository;
import com.prodapt.capstoneproject.service.AccountsServiceImpl;

@SpringBootTest
public class AccountServiceTest {

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
}
