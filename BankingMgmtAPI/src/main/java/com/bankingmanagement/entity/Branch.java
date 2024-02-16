package com.bankingmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "Branch_TBL")
public class Branch implements Serializable {

     private  static final long serialVersionId = 4343434L;

     @Id
     @Column(name = "Branch_ID")
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int branchId;

     @Column(name = "Branch_Name")
     private  String branchName;

     @Column(name = "Branch_Address")
     private String branchAddress;

     @ManyToOne
     @JoinColumn(name="Bank_Code")
     private Bank bank;

     @OneToMany(mappedBy = "branch")
     private Set<Account> accountSet;
//
//     @OneToMany(mappedBy = "branch")
//     private Set<Loan> loanSet;
}
