package com.prodapt.capstoneproject.model;

import java.time.LocalDate;

import com.prodapt.capstoneproject.entities.EMessage;
import com.prodapt.capstoneproject.entities.EResponse;

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
	private long notificationid;
	private long account_id;
	private LocalDate sendDate;
	private EMessage method;
	private EResponse response;
}
