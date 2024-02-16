package com.bankingmanagement.model;

import com.bankingmanagement.entity.Account;
import com.bankingmanagement.entity.Customer;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CustomerDTO {

    private String custName;
    private String custAddress;
    private Long custPhone;
    private List<AccountDTO> accounts;
    private List<LoanDTO> loans;

    public static CustomerDTO mapCustomerEntity(Customer customer){

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustName(customer.getCustName());
        customerDTO.setCustPhone(customer.getCustPhone());
        customerDTO.setCustAddress(customer.getCustAddress());
        List<AccountDTO> custAccounts;
        if(customer.getAccountSet()!=null)
            custAccounts = customer.getAccountSet().stream().map(AccountDTO::mapAccountEntity).toList();
        List<LoanDTO> custLoans;
        if(customer.getLoan()!=null)
            custLoans = customer.getLoan().stream().map(LoanDTO::mapLoanEntity).toList();

        return customerDTO;

    }

}
