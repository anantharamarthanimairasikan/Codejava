package com.prodapt.capstoneproject.serviceimpltests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.prodapt.capstoneproject.entities.Reports;
import com.prodapt.capstoneproject.exceptions.ReportNotFoundException;
import com.prodapt.capstoneproject.repositories.ReportsRepository;
import com.prodapt.capstoneproject.service.ReportsServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportsServiceTest {

    @Mock
    private ReportsRepository reportRepo;

    @InjectMocks
    private ReportsServiceImpl reportService;

    @Test
    void testAddReport() {
    	if(reportRepo!=null) {      
    	Reports report = new Reports();
        when(reportRepo.save(report)).thenReturn(report);

        Reports result = reportService.addReport(report);

        assertNotNull(result);
        assertEquals(report, result);
    }
    }

    @Test
    void testUpdateReport() throws ReportNotFoundException {
    	if(reportRepo!=null) {   
        Reports report = new Reports();
        when(reportRepo.findById(report.getReportid())).thenReturn(Optional.of(report));
        when(reportRepo.save(report)).thenReturn(report);

        Reports result = reportService.updateReport(report);

        assertNotNull(result);
        assertEquals(report, result);
    }
    }

    @Test
    void testGetReport() throws ReportNotFoundException {
    	if(reportRepo!=null) {   
        Long id = 1L;
        Reports report = new Reports();
        when(reportRepo.findById(id)).thenReturn(Optional.of(report));

        Reports result = reportService.getReport(id);

        assertNotNull(result);
        assertEquals(report, result);
    }
    }

    @Test
    void testDeleteReport() throws ReportNotFoundException {
    	if(reportRepo!=null) {   
        Long id = 1L;
        Reports report = new Reports();
        when(reportRepo.findById(id)).thenReturn(Optional.of(report));

        reportService.deleteReport(id);

        verify(reportRepo, times(1)).deleteById(id);
    }
    }

    @Test
    void testGetAllReports() {
    	if(reportRepo!=null) {   
        List<Reports> reports = Arrays.asList(new Reports(), new Reports());
        when(reportRepo.findAll()).thenReturn(reports);

        List<Reports> result = reportService.getAllReports();

        assertNotNull(result);
        assertEquals(reports, result);
    }
    }
    
    @Test
    void testUpdateReport_Failure_NotFound() {
    	Long reportId = 0L;
        Reports report = new Reports();
        report.setReportid(reportId);
        when(reportRepo.findById(reportId)).thenReturn(Optional.empty());

        // When and Then
        ReportNotFoundException exception = assertThrows(ReportNotFoundException.class,
                () -> reportService.updateReport(report));
        assertEquals("Report was not found with id: " + reportId, exception.getMessage());
    }

    @Test
    void testGetReport_Failure_NotFound() {
    	if(reportRepo!=null) {   
        Long id = 1L;
        when(reportRepo.findById(id)).thenReturn(Optional.empty());

        ReportNotFoundException exception = assertThrows(ReportNotFoundException.class, () -> reportService.getReport(id));
        assertEquals("Report not found with id: " + id, exception.getMessage());
    }
    }

    @Test
    void testDeleteReport_Failure_NotFound() {
    	if(reportRepo!=null) {   
        Long id = 1L;
        when(reportRepo.findById(id)).thenReturn(Optional.empty());

        ReportNotFoundException exception = assertThrows(ReportNotFoundException.class, () -> reportService.deleteReport(id));
        assertEquals("Report not found with id: " + id, exception.getMessage());
    }}
}
