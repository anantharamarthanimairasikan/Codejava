package com.prodapt.capstoneproject.entities;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="reports")
public class Reports {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long reportid;
	@Column(nullable = false)
	private LocalDate generatedDate;
	@Enumerated(EnumType.STRING)
	private EReport type;
	@ManyToOne(cascade =CascadeType.ALL)
	@JoinColumn(name="adminid")
	private Admin admin;
 
 
}
 
 
