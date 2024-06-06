package com.prodapt.capstoneproject.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name="account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountid;
    @OneToOne(cascade = CascadeType.ALL)
    private Customer customer;
    @OneToMany(mappedBy="account")
    private List<Payments> payments;
    @OneToMany(mappedBy="account")
    private List<Notification> notifications;
    @Column(nullable = false)
    private LocalDate creationDate;
    private LocalDate dueDate;
    private Integer amount;
    private LocalDate lastpayementDate;
    
}