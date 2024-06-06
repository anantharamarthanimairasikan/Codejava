package com.prodapt.capstoneproject.service;

import java.util.List;

import com.prodapt.capstoneproject.entities.Account;
import com.prodapt.capstoneproject.exceptions.AccountNotFoundException;

public interface AccountService {

	Account addAccount(Account account);

	Account updateAccount(Account account) throws AccountNotFoundException;

	Account getAccount(Long id) throws AccountNotFoundException;

	void deleteAccount(Long id) throws AccountNotFoundException;

	List<Account> getAllAccounts();
}