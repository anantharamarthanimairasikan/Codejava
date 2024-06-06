package com.prodapt.capstoneproject.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.prodapt.capstoneproject.entities.Account;
@Repository
public interface AccountsRepository extends CrudRepository<Account, Long> {

}
