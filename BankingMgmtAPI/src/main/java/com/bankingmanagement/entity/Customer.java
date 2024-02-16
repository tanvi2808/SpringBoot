package com.bankingmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Customer_TBL")
public class Customer implements Serializable {

    private  static final long serialVersionId = 2432343545l;

    @Id
    @Column(name = "Cust_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int custId;

    @Column(name = "Cust_Name")
    private String custName;

    @Column(name = "Cust_Phone")
    private long custPhone;

    @Column(name = "Cust_Address")
    private String custAddress;

    @OneToMany(mappedBy = "customer")
    private Set<Account> accountSet;

    @OneToMany(mappedBy = "customer")
    private Set<Loan> Loan;


}
