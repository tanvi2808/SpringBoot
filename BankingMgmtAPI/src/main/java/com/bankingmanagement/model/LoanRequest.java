package com.bankingmanagement.model;

import com.bankingmanagement.entity.Branch;
import com.bankingmanagement.entity.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanRequest {

    private int loanId;

    @NotNull
    private String loanType;


    private Double loanAmount =0.00;

    @NotNull
    private int custId;

    @NotNull
    private int branchId;
}
