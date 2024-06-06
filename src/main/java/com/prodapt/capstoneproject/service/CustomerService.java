package com.prodapt.capstoneproject.service;

import com.prodapt.capstoneproject.entities.Customer;
import com.prodapt.capstoneproject.exceptions.CustomerNotFoundException;
import com.prodapt.capstoneproject.model.CustomerStatusReport;

import java.util.List;

public interface CustomerService {

	Customer addCustomer(Customer customer);

	Customer updateCustomer(Customer customer)throws CustomerNotFoundException;

	Customer getCustomer(Integer id) throws CustomerNotFoundException;

	void deleteCustomer(Integer id) throws CustomerNotFoundException;

	List<Customer> getAllCustomers();
	
	List<CustomerStatusReport> getCustomerStatusReport();
	
	
}