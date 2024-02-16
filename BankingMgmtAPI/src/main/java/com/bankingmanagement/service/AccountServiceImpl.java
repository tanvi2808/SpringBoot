package com.bankingmanagement.service;

import com.bankingmanagement.entity.Account;
import com.bankingmanagement.entity.Branch;
import com.bankingmanagement.entity.Customer;
import com.bankingmanagement.exceptions.AccountNotFoundException;
import com.bankingmanagement.model.AccountDTO;
import com.bankingmanagement.model.AccountRequest;
import com.bankingmanagement.model.CustomerDTO;
import com.bankingmanagement.repository.AccountRepository;
import com.bankingmanagement.repository.BranchRepository;
import com.bankingmanagement.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    BranchRepository  branchRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public List<AccountDTO> findAllAccounts() throws AccountNotFoundException{
        List<Account> accounts = accountRepository.findAll();
        if(CollectionUtils.isEmpty(accounts))
            throw new AccountNotFoundException("No accounts were found in Database");
        List<AccountDTO> accountDTOs = accounts.stream().map(account -> {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setAccountType(account.getAccountType());
            accountDTO.setAccountBalance(account.getAccountBalance());
            Branch accountBranch = account.getBranch();
            if(!Objects.isNull(accountBranch))
                accountDTO.setBranchName(accountBranch.getBranchName());
            Customer accountCustomer = account.getCustomer();
            if(!Objects.isNull(accountCustomer))
                accountDTO.setCustomerName(accountCustomer.getCustName());
            return accountDTO;
        }).collect(Collectors.toList());
        return accountDTOs;
    }

    public AccountDTO addNewAccount(AccountRequest accountRequest) throws AccountNotFoundException{
        Account account = new Account();
        account.setAccountType(accountRequest.getAccountType());
        account.setAccountBalance(accountRequest.getAccountBalance());
        Optional<Branch> accBranch = branchRepository.findById(accountRequest.getBranchCode());

        if(accBranch.isPresent())
            account.setBranch(accBranch.get());
        Optional<Customer> accCustomer = customerRepository.findById(accountRequest.getCustId());
        if(accCustomer.isPresent())
            account.setCustomer(accCustomer.get());

        Account newAcc = accountRepository.save(account);

        AccountDTO accountDTO = AccountDTO.mapAccountEntity(newAcc);

        return accountDTO;
    }

    public AccountDTO updateAccount(AccountRequest accountRequest) throws AccountNotFoundException{
        log.info("Updating the account number :{}", accountRequest.getAccountNumber());
        Account account = new Account();
        account.setAccountNumber(accountRequest.getAccountNumber());
        account.setAccountType(accountRequest.getAccountType());
        account.setAccountBalance(accountRequest.getAccountBalance());
        Optional<Branch> accBranch = branchRepository.findById(accountRequest.getBranchCode());

        if(accBranch.isPresent())
            account.setBranch(accBranch.get());
        Optional<Customer> accCustomer = customerRepository.findById(accountRequest.getCustId());
        accCustomer.ifPresent(account::setCustomer);

        Account newAcc = accountRepository.save(account);

        return AccountDTO.mapAccountEntity(newAcc);
    }

    public void deleteAccount(int accNumber) throws AccountNotFoundException{
        log.info("Deleting the account from account Number : {} from the DB", accNumber);
        Optional<Account> accountToBeDeleted = accountRepository.findById(accNumber);
        if(accountToBeDeleted.isEmpty())
            throw new AccountNotFoundException("The account to be deleted is not present in DB");

        accountRepository.deleteById(accNumber);
       // return "Account deleted successfully";

    }

    @Override
    public AccountDTO findAccById(int id) throws AccountNotFoundException {
       log.info("Inside Account Service : find Account by Id {}", id);
       Optional<Account> act = accountRepository.findById(id);
       if(act.isEmpty())
           throw new AccountNotFoundException("Account not found");

       return AccountDTO.mapAccountEntity(act.get());
    }
}
