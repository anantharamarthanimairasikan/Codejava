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
		if (!existingAccount.isPresent()) {
			throw new AccountNotFoundException("Account was not found with ID: " + account.getAccountid());
		}
		Account updatedAccount = accountrep.save(account);
		return updatedAccount;
	}

	@Override
	public Account getAccount(Long id) throws AccountNotFoundException  {
		Optional<Account> account = accountrep.findById(id);
		if (!account.isPresent()) {
			throw new AccountNotFoundException("Account not found with ID: " + id);
		}
		return account.get();
	}

	@Override
	public void deleteAccount(Long id) throws AccountNotFoundException{
		Optional<Account> account = accountrep.findById(id);
		if (!account.isPresent()) {
			throw new AccountNotFoundException("Account not found with ID: " + id);
		}
		accountrep.deleteById(id);
	}

	@Override
	public List<Account> getAllAccounts() {
		return (List<Account>) accountrep.findAll();
	}
	
	
	
	
}