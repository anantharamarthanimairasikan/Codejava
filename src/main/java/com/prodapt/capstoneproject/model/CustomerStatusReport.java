package com.prodapt.capstoneproject.model;

import com.prodapt.capstoneproject.entities.Estatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerStatusReport {
	private String name;
    private String email;
    private long phone;
    private Estatus status;
    private Double overdueAmount;
    private Long communicationCount;
    private String communicationHistory;

}
