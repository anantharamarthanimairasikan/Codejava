package com.prodapt.capstoneproject.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.prodapt.capstoneproject.entities.Account;
@Repository
public interface AccountsRepository extends CrudRepository<Account, Long> {
	public abstract Optional<Account> findByCustomerId(Integer customerId);

}
