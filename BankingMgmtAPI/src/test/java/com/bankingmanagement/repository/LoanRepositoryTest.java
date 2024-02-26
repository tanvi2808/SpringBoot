package com.bankingmanagement.repository;

import com.bankingmanagement.entity.Loan;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LoanRepositoryTest {

    @Autowired
    LoanRepository loanRepository;

    @BeforeEach
    public void setUp(){
        Loan loan = new Loan();
        loan.setLoanAmount(1232.3);
        loan.setLoanType("Car");

        loanRepository.save(loan);
    }

    @Test
    public void findAllLoan_whenLoanPresent_thenReturnLoans(){
       List<Loan> loans =  loanRepository.findAll();
        Assertions.assertEquals(1,loans.size());
    }

    @Test
    public void findbyLoanType_whenLoanPresent_thenReturnLoan(){
        List<Loan> loans =  loanRepository.findByLoanType("Car");
        Assertions.assertEquals(1,loans.size());
    }

    @Test
    public void deleteLoanById_whenLoanPresent_thenNoDataReturn(){
        List<Loan> loans =  loanRepository.findAll();
        Assertions.assertEquals(1, loans.size());
        loanRepository.deleteById(loans.get(0).getLoanId());
        List<Loan> loans2 =  loanRepository.findAll();

        Assertions.assertEquals(0,loans2.size());
    }
}
