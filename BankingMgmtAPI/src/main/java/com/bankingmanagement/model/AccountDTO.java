package com.bankingmanagement.model;

import com.bankingmanagement.entity.Account;
import com.bankingmanagement.entity.Loan;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
public class AccountDTO {

    private String accountType;
    private Double accountBalance;

    private String branchName;
    private String customerName;

    public static AccountDTO mapAccountEntity(Account account){
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountType(account.getAccountType());
        accountDTO.setAccountBalance(account.getAccountBalance());
        if(!Objects.isNull(account.getBranch()))
            accountDTO.setBranchName(account.getBranch().getBranchName());
        if(!Objects.isNull(account.getCustomer()))
            accountDTO.setCustomerName(account.getCustomer().getCustName());

        return accountDTO;
    }


}
