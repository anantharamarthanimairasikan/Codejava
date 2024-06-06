package com.prodapt.capstoneproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prodapt.capstoneproject.entities.Admin;
import com.prodapt.capstoneproject.exceptions.AdminNotFoundException;
import com.prodapt.capstoneproject.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService aservice;

    @PostMapping("/updateadmin")
    public Admin updateCustomer(@RequestBody Admin admin) throws AdminNotFoundException {
        return aservice.Updateadmin(admin);
    }

    @GetMapping("/findall")
    public List<Admin> findAllCustomers() {
        return aservice.findAll();
    }

    @GetMapping("/findbyid/{id}")
    public Admin findCustomerById(@PathVariable Long id) throws AdminNotFoundException {
        return aservice.findById(id);
    }

    @DeleteMapping("/deletebyid/{id}")
    public void deleteCustomerById(@PathVariable Long id) throws AdminNotFoundException {
        aservice.delete(id);
    }

    @DeleteMapping("/deleteall")
    public void deleteAllCustomers() {
        aservice.deleteAll();
    }
}