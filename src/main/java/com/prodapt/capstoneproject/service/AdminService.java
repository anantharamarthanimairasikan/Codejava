package com.prodapt.capstoneproject.service;

import com.prodapt.capstoneproject.entities.Admin;
import com.prodapt.capstoneproject.exceptions.AdminNotFoundException;

import java.util.List;

public interface AdminService{

    Admin Updateadmin(Admin admin) throws AdminNotFoundException;

    List<Admin> findAll();

    Admin findById(Long id)throws AdminNotFoundException;

    void delete(Long id)throws AdminNotFoundException;

    void deleteAll();
}