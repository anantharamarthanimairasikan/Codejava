package com.prodapt.capstoneproject.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodapt.capstoneproject.entities.Account;
import com.prodapt.capstoneproject.exceptions.AccountNotFoundException;
import com.prodapt.capstoneproject.repositories.AccountsRepository;

@Service
public class AccountsServiceImpl implements AccountService {

    @Autowired
    private AccountsRepository accountsRepository;

    @Override
    public Account addAccount(Account account) {
        return accountsRepository.save(account);
    }

    @Override
    public Account updateAccount(Account account) throws AccountNotFoundException {
        // findAccount method will throw AccountNotFoundException if the account does not exist
        findAccount(account.getAccountid());
        return accountsRepository.save(account);
    }

    @Override
    public Account findAccount(Long id) throws AccountNotFoundException {
        return accountsRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + id));
    }

    @Override
    public void deleteAccount(Long id) throws AccountNotFoundException {
        // findAccount method will throw AccountNotFoundException if the account does not exist
        findAccount(id);
        accountsRepository.deleteById(id);
    }

    @Override
    public List<Account> getAllAccounts() {
        return (List<Account>) accountsRepository.findAll();
    }

    @Override
    public Account findAccountByCustomerId(Integer id) throws AccountNotFoundException {
        return accountsRepository.findByCustomerId(id)
                .orElseThrow(() -> new AccountNotFoundException("Account was not found with customer ID: " + id));
    }
}
