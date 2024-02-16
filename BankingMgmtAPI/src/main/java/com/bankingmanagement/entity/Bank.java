package com.bankingmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "Bank_TBL")
public class Bank implements Serializable {

    private  static final long serialVersionId = 12323232L;

    @Id
    @Column(name = "Bank_Code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bankCode;

    @Column(name = "Bank_Name")
    private String bankName;

    @Column(name = "Bank_Address")
    private String bankAddress;

    @OneToMany(mappedBy = "bank", fetch=FetchType.EAGER)
    private Set<Branch> branchSet;


}
