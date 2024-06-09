package com.prodapt.capstoneproject.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prodapt.capstoneproject.entities.Account;
import com.prodapt.capstoneproject.entities.EResponse;
import com.prodapt.capstoneproject.entities.Epaymethod;
import com.prodapt.capstoneproject.entities.Payments;
import com.prodapt.capstoneproject.exceptions.AccountNotFoundException;
import com.prodapt.capstoneproject.model.PaymentReq;
import com.prodapt.capstoneproject.service.AccountService;
import com.prodapt.capstoneproject.service.PaymentService;

import jakarta.transaction.Transactional;

@RestController
@Transactional
@RequestMapping("/account")
@PreAuthorize("hasERole('CUSTOMER')")
public class AccountController {

	@Autowired
	AccountService aservice;
	
	@Autowired
	PaymentService pservice;

	@GetMapping("/getallaccounts")
	public ResponseEntity<List<Account>> getAllAccounts() {
		List<Account> accounts = aservice.getAllAccounts();
		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}

	@PostMapping("/addaccount")
	public ResponseEntity<Account> addAccount(@RequestBody Account account) {
		Account newAccount = aservice.addAccount(account);
		return new ResponseEntity<>(newAccount, HttpStatus.OK);
	}

	@GetMapping("/getaccount/{id}")
	public ResponseEntity<Account> getAccount(@PathVariable Long id) throws AccountNotFoundException {
		Account account = aservice.getAccount(id);
		return new ResponseEntity<>(account, HttpStatus.OK);
	}

	@PutMapping("/updateaccount")
	public ResponseEntity<Account> updateAccount(@RequestBody Account account) throws AccountNotFoundException {
		Account updatedAccount = aservice.updateAccount(account);
		return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
	}

	@DeleteMapping("/deleteaccount/{id}")
	public ResponseEntity<Void> deleteAccount(@PathVariable Long id) throws AccountNotFoundException {
		aservice.deleteAccount(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/makepayment")
	public ResponseEntity<Payments> makepayment(@RequestBody PaymentReq payreq) throws AccountNotFoundException {
		Payments payment = new Payments();
		payment.setAccount(aservice.getAccount(payreq.getAccountid()));
		payment.setMethod(Epaymethod.valueOf(new Random().nextInt(EResponse.values().length)));
		payment.setAmount(payreq.getAmount());
		payment.setPaymentDate(LocalDate.now());
		return new ResponseEntity<>(payment, HttpStatus.OK);
	}
}