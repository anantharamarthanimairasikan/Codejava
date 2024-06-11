package com.prodapt.capstoneproject.controllertests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.prodapt.capstoneproject.controller.ReportsController;
import com.prodapt.capstoneproject.entities.Admin;
import com.prodapt.capstoneproject.entities.EReport;
import com.prodapt.capstoneproject.entities.Reports;
import com.prodapt.capstoneproject.exceptions.ReportNotFoundException;
import com.prodapt.capstoneproject.service.ReportService;

public class ReportControllerTest {
	@InjectMocks
    private ReportsController reportsController;
 
    @Mock
    private ReportService reportService;
 
    private Reports report;
    private List<Reports> reportList;
 
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Admin admin = new Admin();
        admin.setAdminid(1L);
        report = new Reports();
        report.setReportid(1L);
        report.setGeneratedDate(LocalDate.now());
        report.setType(EReport.PERFORMANCE_DASHBOARD); // Use a valid EReport enum value
        report.setAdmin(admin);
 
        reportList = Arrays.asList(report);
    }
    @Test
    public void testCreateReport_Success() {
        when(reportService.addReport(report)).thenReturn(report);
        ResponseEntity<String> response = reportsController.createReport(report);
 
        verify(reportService).addReport(report);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Report created successfully", response.getBody());
    }
 
    @Test
    public void testGetAllReports_Success() {
        when(reportService.getAllReports()).thenReturn(reportList);
        ResponseEntity<List<Reports>> response = reportsController.getAllReports();
 
        verify(reportService).getAllReports();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reportList, response.getBody());
    }
 
    @Test
    public void testGetReportById_Success() throws ReportNotFoundException {
        Long id = 1L;
        when(reportService.getReport(id)).thenReturn(report);
        ResponseEntity<Reports> response = reportsController.getReportById(id);
 
        verify(reportService).getReport(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(report, response.getBody());
    }
 
    @Test
    public void testUpdateReport_Success() throws ReportNotFoundException {
        when(reportService.updateReport(report)).thenReturn(report);
        ResponseEntity<String> response = reportsController.updateReport(report);
 
        verify(reportService).updateReport(report);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Report updated successfully", response.getBody());
    }
 
    @Test
    public void testDeleteReport_Success() throws ReportNotFoundException {
        Long id = 1L;
        doNothing().when(reportService).deleteReport(id);
        ResponseEntity<String> response = reportsController.deleteReport(id);
 
        verify(reportService).deleteReport(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Report deleted successfully", response.getBody());
    }
 
    @Test
    public void testGetReportById_NotFound() throws ReportNotFoundException {
        Long id = 2L;
        when(reportService.getReport(id)).thenThrow(new ReportNotFoundException("Report not found"));
 
        ReportNotFoundException exception = assertThrows(ReportNotFoundException.class, () -> {
            reportsController.getReportById(id);
        });
 
        assertEquals("Report not found", exception.getMessage());
        ResponseEntity<String> response = reportsController.handleReportNotFoundException(exception);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Report not found", response.getBody());
    }
 
    @Test
    public void testUpdateReport_NotFound() throws ReportNotFoundException {
        doThrow(new ReportNotFoundException("Report not found")).when(reportService).updateReport(report);
 
        ReportNotFoundException exception = assertThrows(ReportNotFoundException.class, () -> {
            reportsController.updateReport(report);
        });
 
        assertEquals("Report not found", exception.getMessage());
        ResponseEntity<String> response = reportsController.handleReportNotFoundException(exception);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Report not found", response.getBody());
    }
 
    @Test
    public void testDeleteReport_NotFound() throws ReportNotFoundException {
        Long id = 2L;
        doThrow(new ReportNotFoundException("Report not found")).when(reportService).deleteReport(id);
 
        ReportNotFoundException exception = assertThrows(ReportNotFoundException.class, () -> {
            reportsController.deleteReport(id);
        });
 
        assertEquals("Report not found", exception.getMessage());
        ResponseEntity<String> response = reportsController.handleReportNotFoundException(exception);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Report not found", response.getBody());
    }

}
