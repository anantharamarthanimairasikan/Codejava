package com.prodapt.capstoneproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Admin updateAdmin(Admin admin) throws AdminNotFoundException {
        Optional<Admin> existingAdmin = adminRepository.findById(admin.getAdminid());
        if (existingAdmin.isPresent()) {
            return adminRepository.save(admin);
        } else {
            throw new AdminNotFoundException("Admin was not found with id: " + admin.getAdminid());
        }
    }

    @Override
    public List<Admin> findAll() {
        return (List<Admin>) adminRepository.findAll();
    }

    @Override
    public Admin findById(Long id) throws AdminNotFoundException {
        return getAdminOrThrow(id);
    }

    @Override
    public void delete(Long id) throws AdminNotFoundException {
    	getAdminOrThrow(id);
        adminRepository.deleteById(id);
        
    }

    @Override
    public void deleteAll() {
        adminRepository.deleteAll();
    }

    @Override
    public List<AdminActionsReport> getAdminActionReport() {
        List<Object[]> results = adminRepository.findAdminActions();
        List<AdminActionsReport> reports = new ArrayList<>();
        for (Object[] result : results) {
            AdminActionsReport report = new AdminActionsReport();
            report.setAdminid((Long) result[ADMIN_ID_INDEX]);
            report.setAdminactions((Long) result[ACTIONS_INDEX]);
            report.setReactivations((Long) result[REACTIVATIONS_INDEX]);
            reports.add(report);
        }
        return reports;
    }

    private Admin getAdminOrThrow(Long id) throws AdminNotFoundException {
        Optional<Admin> admin = adminRepository.findById(id);
        if (admin.isPresent()) {
            return admin.get();
        } else {
            throw new AdminNotFoundException("Admin not found with id: " + id);
        }
    }

    private static final int ADMIN_ID_INDEX = 0;
    private static final int ACTIONS_INDEX = 1;
    private static final int REACTIVATIONS_INDEX = 2;
    
    @Override
    public Admin findByUsername(String username) throws AdminNotFoundException {
    	Optional<Admin> admin = adminRepository.findByUsername(username);
    	if(admin.isPresent()) {
    		return admin.get();
    	}else {
    		throw new AdminNotFoundException("No admins was found by username:"+username);
    	}
       
    }
}