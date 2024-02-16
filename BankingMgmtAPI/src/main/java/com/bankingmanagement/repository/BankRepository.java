package com.bankingmanagement.repository;

import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.model.BankDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank, Integer> {

     Optional<Bank> findByBankName(String name);
     void deleteByBankName(String name);
}
