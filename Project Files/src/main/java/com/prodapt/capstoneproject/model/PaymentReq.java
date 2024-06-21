package com.prodapt.capstoneproject.model;

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
public class PaymentReq {
	
	private Integer amount;
	private Integer  customerid;
	private Epaymethod paymethod;

}
