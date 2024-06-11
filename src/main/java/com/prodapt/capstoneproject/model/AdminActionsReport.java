package com.prodapt.capstoneproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdminActionsReport {
	private Long adminid;
	private Long adminactions;
	private Long reactivations;

}
