package com.prodapt.capstoneproject.model;

import com.prodapt.capstoneproject.entities.Estatus;

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
public class CustomerStatusReport {
	private String name;
    private String email;
    private long phone;
    private Estatus status;
    private Integer overdueAmount;
    private Integer communicationCount;
    private String communicationHistory;

}
