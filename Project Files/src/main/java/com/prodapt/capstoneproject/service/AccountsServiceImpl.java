package com.prodapt.capstoneproject.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.prodapt.capstoneproject.entities.Account;
import com.prodapt.capstoneproject.entities.Customer;
import com.prodapt.capstoneproject.entities.Notification;
import com.prodapt.capstoneproject.exceptions.AccountNotFoundException;
import com.prodapt.capstoneproject.repositories.AccountsRepository;
import com.prodapt.capstoneproject.repositories.CustomerRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class AccountsServiceImpl implements AccountService {
	
	@Autowired
    private CustomerRepository customerRepository;
	
	@Autowired
    private NotificationService repo;

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
    
    @Transactional
    @Scheduled(cron = "0 14 22 * * ?")
    public void sendPaymentReminders() {
        log.info("Starting sendPaymentReminders() method execution");
        List<Customer> customers = (List<Customer>) customerRepository.findAll();

        for (Customer customer : customers) {
                Optional<Account> accountOptional = accountsRepository.findById(customer.getId().longValue());

                if (accountOptional.isPresent()) {
                    Account account = accountOptional.get();
                    LocalDate dueDate = account.getDueDate();
                    LocalDate currentDate = LocalDate.now();
                    long daysDifference = ChronoUnit.DAYS.between(currentDate, dueDate);
                    Integer amount = account.getAmount();

                        if (daysDifference <= 5 && daysDifference == 0) {
                            Notification notification = new Notification();
                            String message = "Your payment is due in " + daysDifference + " day(s). Please make the payment to avoid any late fees.";
                            notification.setAccount(account);
                            notification.setSendDate(LocalDate.now());
                            notification.setMessage(message);
                            repo.addNotification(notification);  // Assuming repo is your NotificationRepository
                        } else if (daysDifference > 0 && daysDifference < 15) {
                            long overdueDays = Math.abs(daysDifference);
                            Notification notification = new Notification();
                            String message = "Your payment is overdue by " + overdueDays + " day(s). Please make the payment immediately to avoid further penalties.";
                            notification.setAccount(account);
                            notification.setSendDate(LocalDate.now());
                            notification.setMessage(message);
                            repo.addNotification(notification); // Assuming repo is your NotificationRepository
                        } else if (daysDifference > 15) {
                            Notification notification = new Notification();
                            String message = "Your account is deactivated. Please contact the administrator.";
                            notification.setAccount(account);
                            notification.setSendDate(LocalDate.now());
                            notification.setMessage(message);
                            repo.addNotification(notification); // Assuming repo is your NotificationRepository
                        }
                   
                }
        }
        log.info("Finished sendPaymentReminders() method execution");
    }


}
