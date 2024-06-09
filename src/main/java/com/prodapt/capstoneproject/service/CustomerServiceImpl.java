package com.prodapt.capstoneproject.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodapt.capstoneproject.entities.Customer;
import com.prodapt.capstoneproject.entities.Estatus;
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
			throw new CustomerNotFoundException("Customer was not found with id: " + customer.getId());
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
			 List<Object[]> results = custrep.getCustomerStatusReport();
		        List<CustomerStatusReport> reports = new ArrayList<>();
		        for (Object[] result : results) {
		            CustomerStatusReport report = new CustomerStatusReport();
		            report.setName((String) result[0]);
		            report.setEmail((String) result[1]);
		            report.setPhone((Long) result[2]);
		            report.setStatus(Estatus.valueOf((String) result[3]));
		            report.setOverdueAmount(((BigDecimal) result[4]).intValue());
		            report.setCommunicationCount(((Integer) result[5]));
		            report.setCommunicationHistory((String) result[6]);
		            reports.add(report);
		        }
		        return reports;
		    }
	}
