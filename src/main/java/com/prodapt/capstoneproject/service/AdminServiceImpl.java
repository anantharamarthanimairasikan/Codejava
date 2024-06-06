package com.prodapt.capstoneproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodapt.capstoneproject.entities.Admin;
import com.prodapt.capstoneproject.exceptions.AdminNotFoundException;
import com.prodapt.capstoneproject.repositories.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
	public
    Admin Updateadmin(Admin admin) throws AdminNotFoundException {
        if (adminRepository.findById(admin.getAdminid()).isEmpty()) {
            throw new AdminNotFoundException("Admin not found with id: " + admin.getAdminid());
        }
        return adminRepository.save(admin);
    }

    @Override
    public List<Admin> findAll() {
        return (List<Admin>) adminRepository.findAll();
    }

    @Override
    public Admin findById(Long id) throws AdminNotFoundException {
        Admin admin = adminRepository.findById(id).orElse(null);
        if (admin == null) {
            throw new AdminNotFoundException("Admin not found with id: " + id);
        }
        return admin;
    }

    @Override
    public void delete(Long id) throws AdminNotFoundException {
        if (adminRepository.findById(id).orElse(null) == null) {
            throw new AdminNotFoundException("Admin not found with id: " + id);
        }
        adminRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        adminRepository.deleteAll();
    }
}