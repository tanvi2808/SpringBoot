package com.bankingmanagement.model;

import com.bankingmanagement.entity.Bank;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BranchDTO {
    private String branchName;
    private String branchAddress;
    private String bankName;

    public BranchDTO(String branchName, String branchAddress, String bankName) {
        this.branchName = branchName;
        this.branchAddress = branchAddress;
        this.bankName = bankName;
    }
}
