package com.prodapt.capstoneproject.model;

import java.util.Set;

import com.prodapt.capstoneproject.entities.Customer;

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
public class UsersList {
	private Set<Customer> customers;

}
