package com.bankingmanagement.entity;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "Account_TBL")
public class Account implements Serializable {

    private  static final long serialVersionId = 23243545l;

    @Id
    @Column(name = "Account_Number")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountNumber;

    @Column(name = "Account_Type")
    private String accountType;

    @Column(name = "Account_Balance")
    private Double accountBalance;

    @ManyToOne
    @JoinColumn(name = "Branch_ID")
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "Cust_ID")
    private Customer customer;

}
