package com.prodapt.capstoneproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prodapt.capstoneproject.entities.Reports;
import com.prodapt.capstoneproject.exceptions.ReportNotFoundException;
import com.prodapt.capstoneproject.service.ReportService;

import jakarta.transaction.Transactional;

@RestController
@Transactional
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReportsController {
	
	@Autowired
    ReportService rservice;
    // Create a new report
    @PostMapping("/addreport")
    public ResponseEntity<String> createReport(@RequestBody Reports report) {
        rservice.addReport(report);
        return new ResponseEntity<>("Report created successfully", HttpStatus.CREATED);
    }
    // Get all reports
    @GetMapping("/getallreports")
    public ResponseEntity<List<Reports>> getAllReports() {
        List<Reports> reports = rservice.getAllReports();
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }
    // Get a report by ID
    @GetMapping("/getreport/{id}")
    public ResponseEntity<Reports> getReportById(@PathVariable Long id) throws ReportNotFoundException {
        Reports report = rservice.getReport(id);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }
    // Update a report
    @PostMapping("/updatereport")
    public ResponseEntity<String> updateReport(@RequestBody Reports report) throws ReportNotFoundException {
        rservice.updateReport(report);
        return new ResponseEntity<>("Report updated successfully", HttpStatus.OK);
    }
    // Delete a report
    @DeleteMapping("/deletereport/{id}")
    public ResponseEntity<String> deleteReport(@PathVariable Long id) throws ReportNotFoundException {
        rservice.deleteReport(id);
        return new ResponseEntity<>("Report deleted successfully", HttpStatus.OK);
    }
 
    // Exception handler for ReportNotFoundException
    @ExceptionHandler(ReportNotFoundException.class)
    public ResponseEntity<String> handleReportNotFoundException(ReportNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}