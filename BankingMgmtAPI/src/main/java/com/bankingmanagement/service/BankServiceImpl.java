package com.bankingmanagement.service;

import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.entity.Branch;
import com.bankingmanagement.exceptions.BankNotFoundException;
import com.bankingmanagement.exceptions.BranchNotFoundException;
import com.bankingmanagement.model.BankDTO;
import com.bankingmanagement.model.BankRequest;
import com.bankingmanagement.model.BranchDTO;
import com.bankingmanagement.repository.BankRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BankServiceImpl implements BankService {

    @Autowired
    BankRepository bankRepository;

    @Override
    public List<BankDTO> findAllBanks() throws BankNotFoundException {
        log.info("Inside BankService Implementation");
        List<Bank> banks = bankRepository.findAll();

        if(CollectionUtils.isEmpty(banks))
            throw new BankNotFoundException("No Bank found in the DB");

        List<BankDTO> BankDTOs = banks.stream().map(bank -> {
            BankDTO bankDTO = new BankDTO();
            bankDTO.setBankName(bank.getBankName());
            bankDTO.setBankAddress(bank.getBankAddress());
          //  log.info("branches : " + bank.getBranchSet().size());
            if(!CollectionUtils.isEmpty(bank.getBranchSet())){
                log.info(("branches are not null"));
                Set<Branch> bankBranches = bank.getBranchSet();
                List<BranchDTO> branchDTOs = bankBranches.stream().map(branch -> {
                BranchDTO branchDTO = new BranchDTO();
                branchDTO.setBranchName(branch.getBranchName());
                branchDTO.setBranchAddress(branch.getBranchAddress());
                branchDTO.setBankName(bank.getBankName());
                return branchDTO;
                }).collect(Collectors.toList());

                bankDTO.setBranches(branchDTOs);
            }
            return bankDTO;
        }).collect(Collectors.toList());
    return BankDTOs;

    }

    @Override
    public BankDTO findBankById(int id) throws BankNotFoundException, InterruptedException {

        Thread.sleep(6000);

        Optional<Bank> optionalBank = bankRepository.findById(id);
        if(optionalBank.isEmpty()){
            log.error("No details found for bank with code : " + id);
            throw new BankNotFoundException("No details found");
        }

        Bank bank = optionalBank.get();
        BankDTO bankDTO = new BankDTO();
        bankDTO.setBankName(bank.getBankName());
        bankDTO.setBankAddress(bank.getBankAddress());
        if(!CollectionUtils.isEmpty(bank.getBranchSet())){
            List<BranchDTO> branchDTOS = bank.getBranchSet().stream().map(branch -> {
                BranchDTO branchDTO = new BranchDTO();
                branchDTO.setBranchName(branch.getBranchName());
                branchDTO.setBranchAddress(branch.getBranchAddress());
                branchDTO.setBankName(bank.getBankName());

                return branchDTO;

            }).collect(Collectors.toList());
            bankDTO.setBranches(branchDTOS);
        }

        return bankDTO;
    }

    public BankDTO findByBankName(String name) throws BankNotFoundException, InterruptedException {

        Thread.sleep(6000);
        Optional<Bank> optionalBank = bankRepository.findByBankName(name);
        if(optionalBank.isEmpty()){
            log.error("No details found for bank with code : " + name);
            throw new BankNotFoundException();
        }

        Bank bank = optionalBank.get();
        BankDTO bankDTO = new BankDTO();
        bankDTO.setBankName(bank.getBankName());
        bankDTO.setBankAddress(bank.getBankAddress());
        if(!CollectionUtils.isEmpty(bank.getBranchSet())){
            List<BranchDTO> branchDTOS = bank.getBranchSet().stream().map(branch -> {
                BranchDTO branchDTO = new BranchDTO();
                branchDTO.setBranchName(branch.getBranchName());
                branchDTO.setBranchAddress(branch.getBranchAddress());
                branchDTO.setBankName(bank.getBankName());

                return branchDTO;

            }).collect(Collectors.toList());
            bankDTO.setBranches(branchDTOS);
        }

        return bankDTO;
    }

    public BankDTO addNewBank(BankRequest bankRequest) throws BankNotFoundException {
        Bank bank = new Bank();
        bank.setBankName(bankRequest.getBankName());
        bank.setBankAddress(bankRequest.getBankAddress());

        Bank bankAdded = bankRepository.save(bank);

        if(Objects.isNull(bankAdded))
            throw new BankNotFoundException("Bank Could not added to DB");

        BankDTO bankDTO = new BankDTO();
        bankDTO.setBankName(bankAdded.getBankName());
        bankDTO.setBankAddress(bankAdded.getBankAddress());

        return bankDTO;

    }

    public BankDTO updateBank(BankRequest bankRequest) throws BankNotFoundException {
        Bank bank = new Bank();
        bank.setBankCode(bankRequest.getBankCode());
        bank.setBankName(bankRequest.getBankName());
        bank.setBankAddress(bankRequest.getBankAddress());

        Bank bankAdded = bankRepository.save(bank);

        if(Objects.isNull(bankAdded))
            throw new BankNotFoundException("Bank Could not added to DB");

        BankDTO bankDTO = new BankDTO();
        bankDTO.setBankName(bankAdded.getBankName());
        bankDTO.setBankAddress(bankAdded.getBankAddress());

        return bankDTO;

    }

    @Override
    public String deleteBank(int bankCode) throws BankNotFoundException {
        log.info("Deleting the bank from DB with bank code : {}", bankCode);
        bankRepository.deleteById(bankCode);
        return "Bank with the code deleted";

    }

    @Override
    @Transactional
    public String deleteBankByName(String bankName) throws BankNotFoundException {
        log.info("Deleting the bank with the name {}", bankName);
        Optional<Bank> bank = bankRepository.findByBankName(bankName);

        Bank bank1 = null;
        if(bank.isEmpty())
            throw new BankNotFoundException("Bank was not found");
        bank1 =bank.get();
        log.info("Bank with bank name :{} is found ", bank1.getBankName());

        bankRepository.deleteByBankName(bankName);
        return "Bank Deleted ";
    }
}
