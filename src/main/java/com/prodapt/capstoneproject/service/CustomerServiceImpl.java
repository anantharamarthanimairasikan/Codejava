package com.prodapt.capstoneproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodapt.capstoneproject.entities.Customer;
import com.prodapt.capstoneproject.exceptions.CustomerNotFoundException;
import com.prodapt.capstoneproject.model.CustomerStatusReport;
import com.prodapt.capstoneproject.repositories.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	CustomerRepository custrep;

	@Override
	public Customer addCustomer(Customer customer) {
		return custrep.save(customer);
	}
	
	@Override
	public Customer updateCustomer(Customer customer)throws CustomerNotFoundException {
		if (getCustomer(customer.getId()) == null) {
			throw new CustomerNotFoundException("Customer not found with id: " + customer.getId());
		}
		return custrep.save(customer);
	}

	@Override
	public Customer getCustomer(Integer id) throws CustomerNotFoundException{
		Optional<Customer> customer = custrep.findById(id);
		if (!customer.isPresent()) {
			throw new CustomerNotFoundException("Customer not found with id: " + id);
		}
		return customer.get();
	}

	@Override
	public void deleteCustomer(Integer id)throws CustomerNotFoundException {
		if (getCustomer(id) == null) {
			throw new CustomerNotFoundException("Customer not found with id: " + id);
		}
		custrep.deleteById(id);
	}

	@Override
	public List<Customer> getAllCustomers() {
		return (List<Customer>) custrep.findAll();
	}

	@Override
	public List<CustomerStatusReport> getCustomerStatusReport() {
		
		return custrep.getCustomerStatusReport();
	}

}