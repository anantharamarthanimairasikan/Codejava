package com.prodapt.capstoneproject.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prodapt.capstoneproject.entities.Account;
import com.prodapt.capstoneproject.entities.Admin;
import com.prodapt.capstoneproject.entities.Customer;
import com.prodapt.capstoneproject.entities.ERole;
import com.prodapt.capstoneproject.entities.UserEntity;
import com.prodapt.capstoneproject.repositories.AdminRepository;
import com.prodapt.capstoneproject.repositories.CustomerRepository;
import com.prodapt.capstoneproject.repositories.UserRepository;
import com.prodapt.capstoneproject.security.jwt.JwtUtils;
import com.prodapt.capstoneproject.security.payload.request.LoginRequest;
import com.prodapt.capstoneproject.security.payload.response.JwtResponse;
import com.prodapt.capstoneproject.security.payload.response.MessageResponse;
import com.prodapt.capstoneproject.security.service.UserDetailsImpl;
import com.prodapt.capstoneproject.service.AccountService;
import com.prodapt.capstoneproject.service.CustomerService;

import jakarta.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	CustomerService customerService;
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AccountService accountservice;
	
	@Autowired
	AdminRepository adminRepository;

	@Autowired
	PasswordEncoder encoder;	

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin/customer")
	public ResponseEntity<JwtResponse> authenticateCustomer(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername()));
	}
	@PostMapping("/signin/admin")
	public ResponseEntity<JwtResponse> authenticateAdmin(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
	

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername()));
	}
	
	@PostMapping("/signup/customer")
	public ResponseEntity<MessageResponse> registerCustomer(@RequestBody Customer customer) {
		if (Boolean.TRUE.equals(userRepository.existsByUsername(customer.getUsername()))) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (Boolean.TRUE.equals(customerRepository.existsByEmail(customer.getEmail()))) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}
		
		//Create a new customer
		Customer cust = customerService.addCustomer(customer);
		//Create an account
		Account account = new Account();
		account.setCustomer(cust);
		account.setCreationDate(LocalDate.now());
		accountservice.addAccount(account);
		// Create new user's account
		UserEntity user = new UserEntity();
		user.setUsername(customer.getUsername());
		user.setPassword(encoder.encode(customer.getPassword()));
		user.setRole(ERole.Customer);
		userRepository.save(user);
		
		
		

		return ResponseEntity.ok(new MessageResponse("Customer registered successfully!"));
	}
	
	@PostMapping("/signup/admin")
	public ResponseEntity<MessageResponse> registerAdmin(@RequestBody Admin admin) {
		if (Boolean.TRUE.equals(userRepository.existsByUsername(admin.getUsername()))) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}
		
		// Create new user's account
		UserEntity user = new UserEntity();
		user.setUsername(admin.getUsername());
		user.setPassword(encoder.encode(admin.getPassword()));
		user.setRole(ERole.Support_Admin);
		userRepository.save(user);
		adminRepository.save(admin);
		

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

}
