package com.prodapt.capstoneproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodapt.capstoneproject.entities.Reports;
import com.prodapt.capstoneproject.exceptions.ReportNotFoundException;
import com.prodapt.capstoneproject.repositories.ReportsRepository;

@Service
public class ReportsServiceImpl implements ReportService {
	
	@Autowired
	private ReportsRepository reportRepo;

	@Override
	public Reports addReport(Reports report) {
		return reportRepo.save(report);
	}

	@Override
	public Reports updateReport(Reports report) throws ReportNotFoundException {
		if (getReport(report.getReportid()) == null) {
			throw new ReportNotFoundException("Report not found with id: " + report.getReportid());
		}
		return reportRepo.save(report);
	}

	@Override
	public Reports getReport(Long id) throws ReportNotFoundException {
		Optional<Reports> report = reportRepo.findById(id);
		if (!report.isPresent()) {
			throw new ReportNotFoundException("Report not found with id: " + id);
		}
		return report.get();
	}

	@Override
	public void deleteReport(Long id) throws ReportNotFoundException {
		if (getReport(id) == null) {
			throw new ReportNotFoundException("Report not found with id: " + id);
		}
		reportRepo.deleteById(id);
	}

	@Override
	public List<Reports> getAllReports() {
		return (List<Reports>) reportRepo.findAll();
	}
}