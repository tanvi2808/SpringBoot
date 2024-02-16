package com.bankingmanagement.repository;

import com.bankingmanagement.entity.Account;
import com.bankingmanagement.model.AccountDTO;
import org.aspectj.lang.annotation.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@RunWith(SpringRunner.class)
public class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    public void saveAccount(){
        Account account = new Account();
        account.setAccountNumber(111);
        account.setAccountType("Savings");
        account.setAccountBalance(123.45);

        accountRepository.save(account);
    }


    @Test
    public void findAllAccounts(){
        List<Account> accountList = accountRepository.findAll();
        Assertions.assertEquals(1, accountList.size());

    }

    @Test
    public void findAccountById_whenValidId_thenReturnsAccount(){
        Optional<Account> account= accountRepository.findById(2);

        Assertions.assertTrue(account.isPresent());
        Assertions.assertEquals("Savings", account.get().getAccountType());
    }

    @Test
    public void deleteAccountById_whenValidId_thenDelete(){

        List<Account> accounts = accountRepository.findAll();
        Assertions.assertEquals(1, accounts.size());

       accountRepository.deleteById(accounts.get(0).getAccountNumber());
        List<Account> accountList = accountRepository.findAll();
        Assertions.assertEquals(0,accountList.size());

    }




}
