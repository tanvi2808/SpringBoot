package com.bankingmanagement.model;

import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.entity.Branch;
import com.bankingmanagement.entity.Loan;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class BankDTO {
    private String bankName;
    private String bankAddress;
    private List<BranchDTO> branches;

    public static BankDTO mapBankEntity(Bank bank) {
        BankDTO bankDTO = new BankDTO();
        bankDTO.setBankName(bank.getBankName());
        bankDTO.setBankAddress(bank.getBankAddress());
        List<BranchDTO> branchDTOS = bank.getBranchSet().stream().map(branch -> {
            BranchDTO branchDTO = new BranchDTO();
            branchDTO.setBranchName(branch.getBranchName());
            branchDTO.setBranchAddress(branch.getBranchAddress());
            branchDTO.setBankName(branch.getBank().getBankName());

            return branchDTO;

        }).toList();
        bankDTO.setBranches(branchDTOS);

        return bankDTO;
    }
}
