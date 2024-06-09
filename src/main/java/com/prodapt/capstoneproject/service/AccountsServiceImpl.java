package com.prodapt.capstoneproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodapt.capstoneproject.entities.Account;
import com.prodapt.capstoneproject.exceptions.AccountNotFoundException;
import com.prodapt.capstoneproject.repositories.AccountsRepository;

@Service
public class AccountsServiceImpl implements AccountService {
	
	@Autowired
	AccountsRepository accountrep;
	
	@Override
	public Account addAccount(Account account) {
		return accountrep.save(account);
	}
	
	@Override
	public Account updateAccount(Account account) throws AccountNotFoundException {
		Optional<Account> existingAccount = accountrep.findById(account.getAccountid());
		if (existingAccount.isPresent()) {
			return accountrep.save(account);
		}else {
			throw new AccountNotFoundException("Account was not found with ID: " + account.getAccountid());
		}
		
	}

	@Override
	public Account getAccount(Long id) throws AccountNotFoundException  {
		Optional<Account> account = accountrep.findById(id);
		if (account.isPresent()) {
			return account.get();
		}else {
			throw new AccountNotFoundException("Account not found with ID: " + id);
		}
		
	}

	@Override
	public void deleteAccount(Long id) throws AccountNotFoundException{
		Optional<Account> account = accountrep.findById(id);
		if (account.isPresent()) {
			accountrep.deleteById(id);
		}else {
			throw new AccountNotFoundException("Account not found with ID: " + id);
		}
		
	}

	@Override
	public List<Account> getAllAccounts() {
		return (List<Account>) accountrep.findAll();
	}

	@Override
	public Account getAccountusingCustomerId(Integer id) throws AccountNotFoundException {
		Optional<Account> existingaccount = accountrep.findByCustomerId(id);
		if(existingaccount.isPresent()) {
			return existingaccount.get();
		}else {
			throw new AccountNotFoundException("Account was not found with customer ID: " + id);
		}
	}
	
}