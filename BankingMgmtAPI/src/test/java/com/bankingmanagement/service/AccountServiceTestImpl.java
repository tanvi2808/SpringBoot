package com.bankingmanagement.service;

import com.bankingmanagement.entity.Account;
import com.bankingmanagement.entity.Branch;
import com.bankingmanagement.entity.Customer;
import com.bankingmanagement.exceptions.AccountNotFoundException;
import com.bankingmanagement.model.AccountDTO;
import com.bankingmanagement.model.AccountRequest;
import com.bankingmanagement.repository.AccountRepository;
import com.bankingmanagement.repository.AccountRepositoryTest;
import com.bankingmanagement.repository.BranchRepository;
import com.bankingmanagement.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(value = MockitoExtension.class)
public class AccountServiceTestImpl {
    @Mock
    AccountRepository accountRepository;

    @Mock
    BranchRepository branchRepository;

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    AccountServiceImpl accountService;

    @Test
    public void testFindAllAccounts() throws AccountNotFoundException {
        List<Account> accounts = new ArrayList<>();

        Account account = new Account();
        account.setAccountNumber(1001);
        account.setAccountType("Savings");
        account.setAccountBalance(67.89);
        accounts.add(account);

        Account account2 = new Account();
        account.setAccountNumber(1003);
        account.setAccountType("Personal");
        account.setAccountBalance(34367.00);
        accounts.add(account2);


        when(accountRepository.findAll()).thenReturn(accounts);

        List<AccountDTO> accountDTOS = accountService.findAllAccounts();
        Assertions.assertEquals(2, accountDTOS.size());


    }


    @Test
    public void testFindAllAccountDetails() throws AccountNotFoundException {

        Account account = new Account();
        account.setAccountNumber(1001);
        account.setAccountType("Savings");
        account.setAccountBalance(67.89);


        Branch branch = new Branch();
        branch.setBranchName("HDFC-1");
        branch.setBranchAddress("Delhi");
        branch.setBranchId(1);

        account.setBranch(branch);

        Customer customer = new Customer();
        customer.setCustName("Ashok");
        customer.setCustAddress("New Delhi");
        customer.setCustPhone(333222332);

        account.setCustomer(customer);

        when(accountRepository.findById(anyInt())).thenReturn(Optional.of(account));

        AccountDTO accountDTO = accountService.findAccById(1);
        Assertions.assertEquals("Ashok", accountDTO.getCustomerName());
        Assertions.assertEquals("HDFC-1", accountDTO.getBranchName());


    }


    @Test
    public void testFindAcctById_whenValidID_thenReturnsAccount() throws AccountNotFoundException {
        Account account = new Account();
        account.setAccountNumber(123);
        account.setAccountType("Savings");
        account.setAccountBalance(23232.232);

        when(accountRepository.findById(anyInt())).thenReturn(Optional.of(account));

        AccountDTO accountDTO = accountService.findAccById(1);

        Assertions.assertEquals("Savings", accountDTO.getAccountType());
    }

    @Test
    public void testFindAcc_whenInvalidAccId_thenThrowsExcpetion(){
            Exception exception = assertThrows(AccountNotFoundException.class, () -> {
                 accountService.findAccById(9898);
            });

    }

    @Test
    public void testAddNewAcc_whenValidData_thenReturnsAcc() throws AccountNotFoundException {
        Account account = new Account();
        account.setAccountNumber(123);
        account.setAccountType("Savings");
        account.setAccountBalance(23232.232);

        Branch branch = new Branch();
        branch.setBranchName("HDFC-1");
        branch.setBranchAddress("Delhi");
        branch.setBranchId(1);

        account.setBranch(branch);

        Customer customer = new Customer();
        customer.setCustName("Ashok");
        customer.setCustAddress("New Delhi");
        customer.setCustPhone(333222332);

        when(accountRepository.save(any())).thenReturn(account);
        when(branchRepository.findById(anyInt())).thenReturn(Optional.of(branch));
        when(customerRepository.findById(anyInt())).thenReturn(Optional.of(customer));

        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountBalance(2343.24);
        accountRequest.setAccountType("Personal");
        accountRequest.setAccountNumber(23);
        ;
        AccountDTO accountDTO = accountService.addNewAccount(accountRequest);

        Assertions.assertEquals("Savings", accountDTO.getAccountType());
    }

    @Test
    public void testUpdateAcc_whenValidData_thenReturnsAcc() throws AccountNotFoundException {
        Account account = new Account();
        account.setAccountNumber(123);
        account.setAccountType("Savings");
        account.setAccountBalance(23232.232);

        when(accountRepository.save(any())).thenReturn(account);

        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountBalance(2343.24);
        accountRequest.setAccountType("Personal");
        accountRequest.setAccountNumber(23);
        ;
        AccountDTO accountDTO = accountService.updateAccount(accountRequest);

        Assertions.assertEquals("Savings", accountDTO.getAccountType());
    }
    @Test
    public void testDeleteAcc_whenValidAccId_thenVerifyDeleteCalledOneTime() throws AccountNotFoundException {
        Account account = new Account();
        account.setAccountNumber(123);
        account.setAccountType("Savings");
        account.setAccountBalance(23232.232);

        when(accountRepository.findById(anyInt())).thenReturn(Optional.of(account));


       doNothing().when(accountRepository).deleteById(any());

       accountService.deleteAccount(1);
       verify(accountRepository, times(1)).deleteById(anyInt());

    }

    @Test
    public void testDeleteAcc_whenInvalidId_thenThrowsException(){
        Exception exception = assertThrows(AccountNotFoundException.class, () ->{
             accountService.deleteAccount(1);
        });


    }
}
