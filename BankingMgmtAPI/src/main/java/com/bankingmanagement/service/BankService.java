package com.bankingmanagement.service;

import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.exceptions.BankNotFoundException;
import com.bankingmanagement.model.BankDTO;
import com.bankingmanagement.model.BankRequest;
import com.bankingmanagement.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BankService {

    public List<BankDTO> findAllBanks() throws BankNotFoundException;
    public BankDTO findBankById(int id) throws BankNotFoundException, InterruptedException;
    public BankDTO findByBankName(String name) throws BankNotFoundException, InterruptedException;

    public BankDTO addNewBank(BankRequest bankRequest) throws BankNotFoundException;

    public BankDTO updateBank(BankRequest bankRequest) throws BankNotFoundException;

    public String deleteBank(int bankCode) throws BankNotFoundException;
    public String deleteBankByName(String bankName) throws BankNotFoundException;

}
