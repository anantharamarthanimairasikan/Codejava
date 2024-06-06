package com.prodapt.capstoneproject.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.prodapt.capstoneproject.entities.Payments;
@Repository
public interface PaymentsRepository extends CrudRepository<Payments, Long> {

}
