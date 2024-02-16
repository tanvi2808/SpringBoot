package com.bankingmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRequest {


    private int AccountNumber;

    @NotNull
    private String AccountType;

    @NotNull
    @Min(0)
    private Double AccountBalance;

    @NotNull
    private int  branchCode;
    @NotNull
    private int custId;
}
