package com.prodapt.capstoneproject.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.prodapt.capstoneproject.entities.Reports;
@Repository
public interface ReportsRepository extends CrudRepository<Reports, Long> {

}
