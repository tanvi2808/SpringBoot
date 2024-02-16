package com.bankingmanagement.model;


import com.bankingmanagement.entity.Loan;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanDTO {

    private String loanType;
    private Double LoanAmount;
    private CustomerDTO customer;
    private BranchDTO branchDTO;

    public static LoanDTO mapLoanEntity(Loan loan){
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setLoanType(loan.getLoanType());
        loanDTO.setLoanAmount(loan.getLoanAmount());
       // loanDTO.setCustomer(CustomerDTO.mapCustomerEntity(loan.getCustomer()));

        return loanDTO;
    }

}
