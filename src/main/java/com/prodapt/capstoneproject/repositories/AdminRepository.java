package com.prodapt.capstoneproject.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.prodapt.capstoneproject.entities.Admin;
@Repository
public interface AdminRepository extends CrudRepository<Admin, Long> {
	
	@Query(nativeQuery = true, value = "SELECT a.adminid,COUNT(CASE WHEN r.type = 'CUSTOMER_ACCOUT_STATUS' AND r.type = 'DUNNING_REPORT' THEN 1 ELSE NULL END) as adminactions,COUNT(CASE WHEN r.type = 'PAYMENT_HISTORY' THEN 1 ELSE NULL END) as reactivations FROM reports r JOIN admin a ON r.adminid = a.adminid GROUP BY a.adminid ORDER BY a.adminid DESC")
	List<Object[]> findAdminActions();

}
