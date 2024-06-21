package com.prodapt.capstoneproject.model;

import java.time.LocalDate;

import com.prodapt.capstoneproject.entities.Epaymethod;

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
public class PaymentDetails {
	
	private Long payment_id;
	private String customer_name;
	private String email;
	private Long phone;
	private LocalDate payment_date;
	private Integer amount;
	private Epaymethod method;
	

}
