package com.bankingmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "Loan_TBL")
public class Loan implements Serializable {

    private  static final long serialVersionId = 343543545L;

    @Id
    @Column(name = "Loan_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int loanId;

    @Column(name = "Loan_Type")
    private String loanType;

    @Column(name = "Loan_Amount")
    private Double loanAmount;

    @ManyToOne
    @JoinColumn(name = "Cust_ID")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "Branch_ID")
    private Branch branch;

}
