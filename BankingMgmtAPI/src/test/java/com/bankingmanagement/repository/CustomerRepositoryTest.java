package com.bankingmanagement.repository;


import com.bankingmanagement.entity.Account;
import com.bankingmanagement.entity.Customer;
import com.bankingmanagement.exceptions.CustomerNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.C;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    public void addNewCustomer(){
        Customer customer = new Customer();
        customer.setCustPhone(998877665);
        customer.setCustName("Krisha");
        customer.setCustId(111);
        customer.setCustAddress("Delhi");

        Account account = new Account();
        account.setAccountBalance(100.00);
        account.setAccountNumber(123);
        account.setAccountType("Current");

        Set<Account> accountSet = new HashSet<>();

        customer.setAccountSet(accountSet);

        customerRepository.save(customer);
    }

    @Test
    public void findAllBanks(){

        List<Customer> customerList = customerRepository.findAll();
        Assertions.assertEquals(1, customerList.size());

    }

    @Test
    public void findByCustId_whenValidCustId_thenReturnsCustomer(){

        Optional<Customer> customer = customerRepository.findById(1);
        Assertions.assertTrue(customer.isPresent());
    }

    @Test
    public void findByCustId_whenInValidCustId_thenReturnsCustomer(){

        Optional<Customer> customer = customerRepository.findById(2);
        Assertions.assertTrue(customer.isEmpty());
    }

}
