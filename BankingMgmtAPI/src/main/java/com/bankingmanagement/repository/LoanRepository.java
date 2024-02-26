package com.bankingmanagement.repository;

import com.bankingmanagement.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
    List<Loan> findByLoanType(String type);


}
