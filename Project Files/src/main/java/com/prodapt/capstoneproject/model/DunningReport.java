package com.prodapt.capstoneproject.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DunningReport {
	private LocalDate sendDate;
	private Long notification_id;
	private Integer customer_id;
	private String message;
	private LocalDate response_Date;
	private  Integer response_amount;
	private String outcome;
}
