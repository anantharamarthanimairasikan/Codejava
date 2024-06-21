package com.prodapt.capstoneproject.service;

import java.util.List;

import com.prodapt.capstoneproject.entities.Admin;
import com.prodapt.capstoneproject.exceptions.AdminNotFoundException;

public interface AdminService{

    Admin updateAdmin(Admin admin) throws AdminNotFoundException;

    List<Admin> findAll();

    Admin findById(Long id)throws AdminNotFoundException;

    void delete(Long id)throws AdminNotFoundException;

    void deleteAll();
    

	Admin findByUsername(String username) throws AdminNotFoundException;
}