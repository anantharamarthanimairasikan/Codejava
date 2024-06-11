package com.prodapt.capstoneproject.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodapt.capstoneproject.entities.Customer;
import com.prodapt.capstoneproject.entities.Estatus;
import com.prodapt.capstoneproject.entities.UserEntity;
import com.prodapt.capstoneproject.exceptions.CustomerNotFoundException;
import com.prodapt.capstoneproject.model.CustomerStatusReport;
import com.prodapt.capstoneproject.repositories.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) throws CustomerNotFoundException {
    	findCustomer(customer.getId());
        Customer updatedCustomer = customerRepository.save(customer);
        return updatedCustomer;
    }

    @Override
    public Customer findCustomer(Integer id) throws CustomerNotFoundException {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            return customer.get();
        } else {
            throw new CustomerNotFoundException("Customer not found with id: " + id);
        }
    }

    @Override
    public void deleteCustomer(Integer id) throws CustomerNotFoundException {
    	 Customer customer = findCustomer(id);
    	 customerRepository.deleteById(id);
        
    }

    @Override
    public List<Customer> getAllCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }

    @Override
    public List<CustomerStatusReport> getCustomerStatusReport() {
        List<Object[]> results = customerRepository.getCustomerStatusReport();
        List<CustomerStatusReport> reports = new ArrayList<>();
        for (Object[] result : results) {
            CustomerStatusReport report = new CustomerStatusReport();
            report.setName((String) result[NAME_INDEX]);
            report.setEmail((String) result[EMAIL_INDEX]);
            report.setPhone((Long) result[PHONE_INDEX]);
            report.setStatus(Estatus.valueOf((String) result[STATUS_INDEX]));
            report.setOverdueAmount(((BigDecimal) result[OVERDUE_AMOUNT_INDEX]).intValue());
            report.setCommunicationCount(((Integer) result[COMMUNICATION_COUNT_INDEX]));
            report.setCommunicationHistory((String) result[COMMUNICATION_HISTORY_INDEX]);
            reports.add(report);
        }
        return reports;
    }

    private static final int NAME_INDEX = 0;
    private static final int EMAIL_INDEX = 1;
    private static final int PHONE_INDEX = 2;
    private static final int STATUS_INDEX = 3;
    private static final int OVERDUE_AMOUNT_INDEX = 4;
    private static final int COMMUNICATION_COUNT_INDEX = 5;
    private static final int COMMUNICATION_HISTORY_INDEX = 6;

	@Override
	public Customer findCustomerbyUsername(String username) throws CustomerNotFoundException {
		Optional<Customer> customer = customerRepository.findByUsername(username);
		if (customer.isPresent()) {
            return customer.get();
        } else {
            throw new CustomerNotFoundException("Customer not found with username: " + username);
        }
	}
}