package com.bankingmanagement.repository;


import com.bankingmanagement.entity.Bank;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BankRespositoryTest {

    @Autowired
    private BankRepository bankRepository;

    @Test
    public void findBankByName_whenValidBankName_thenReturnBankDetails(){

        Bank bank = new Bank();
        bank.setBankName("Test");
        bank.setBankAddress("Haryana");

        bankRepository.save(bank);

        Optional<Bank> bankOptional = bankRepository.findByBankName("Test");

        Assert.assertEquals("Test", bankOptional.get().getBankName());
        Assert.assertEquals("Haryana", bankOptional.get().getBankAddress());
    }

    @Test
    public void deleteByBankName_whenValidBankName_thenDelete(){
        Bank bank = new Bank();
        bank.setBankName("Test");
        bank.setBankAddress("Haryana");

        bankRepository.save(bank);
        bankRepository.deleteByBankName("Test");

        Optional<Bank> bankOptional = bankRepository.findByBankName("Test");

        Assert.assertTrue(bankOptional.isEmpty());
    }

}
