package com.prodapt.capstoneproject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodapt.capstoneproject.entities.Admin;
import com.prodapt.capstoneproject.exceptions.AdminNotFoundException;
import com.prodapt.capstoneproject.model.AdminActionsReport;
import com.prodapt.capstoneproject.repositories.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
	public
    Admin Updateadmin(Admin admin) throws AdminNotFoundException {
        if (adminRepository.findById(admin.getAdminid()).isPresent()) {
            return adminRepository.save(admin);
        }else {
        	 throw new AdminNotFoundException("Admin not found with id: " + admin.getAdminid());
        }
        
    }

    @Override
    public List<Admin> findAll() {
        return (List<Admin>) adminRepository.findAll();
    }

    @Override
    public Admin findById(Long id) throws AdminNotFoundException {
        Admin admin = adminRepository.findById(id).orElse(null);
        if (admin != null) {
        	 return admin;
        }else {
        	throw new AdminNotFoundException("Admin not found with id: " + id);
        }
       
    }

    @Override
    public void delete(Long id) throws AdminNotFoundException {
        if (adminRepository.findById(id).orElse(null) != null) {
            adminRepository.deleteById(id);
        }else {
        	throw new AdminNotFoundException("Admin not found with id: " + id);
        }
        
    }

    @Override
    public void deleteAll() {
        adminRepository.deleteAll();
    }

	@Override
	public List<AdminActionsReport> getAdminActionReport() {
		List<Object[]> results = adminRepository.findAdminActions();
		List<AdminActionsReport> reports = new ArrayList<>();
		for(Object[] result: results) {
			AdminActionsReport report = new AdminActionsReport();
			 report.setAdminid((Long) result[0]);
	         report.setAdminactions((Integer) result[1]);
	         report.setReactivations((Integer) result[2]);
	         reports.add(report);
		}
		return reports;
	}
}