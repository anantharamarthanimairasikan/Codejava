package com.prodapt.capstoneproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceDashboardReport {
	private Integer recoveryRate;
	private Integer effectiveness;
	private Double avgResponseTime;
	private Integer total_notifications;
	private Integer total_accounts;
	private Integer ignored_responses;
	private Integer undeliverable_responses;
	
	

}
