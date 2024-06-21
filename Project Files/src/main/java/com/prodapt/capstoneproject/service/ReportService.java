package com.prodapt.capstoneproject.service;

import java.util.List;
import com.prodapt.capstoneproject.entities.Reports;
import com.prodapt.capstoneproject.exceptions.ReportNotFoundException;

public interface ReportService {
	
	Reports addReport(Reports report);

	Reports updateReport(Reports report) throws ReportNotFoundException;

	Reports getReport(Long id) throws ReportNotFoundException;

	void deleteReport(Long id) throws ReportNotFoundException;

	List<Reports> getAllReports();
}