package com.bankingmanagement.service;

import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.entity.Branch;
import com.bankingmanagement.exceptions.BankNotFoundException;
import com.bankingmanagement.model.BankDTO;
import com.bankingmanagement.model.BranchDTO;
import com.bankingmanagement.repository.BankRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Service
public  class AsyncBankServiceImpl implements  AsyncBankService{

    @Autowired
    BankRepository bankRepository;
    @Override
    @Async
    @Cacheable("byBankCode")
    public CompletableFuture<BankDTO> findBankDetails(int code) throws InterruptedException, BankNotFoundException {
        Thread.sleep(6000);
        log.info("Find the bank details with code:{}", code);
        Optional<Bank> bank = bankRepository.findById(code);


        if(bank.isEmpty()){
            log.error("Bank details are not found for the bank code:{}", code);
            throw new BankNotFoundException("Bank details not found");
        }
        Bank bank1 = bank.get();
        BankDTO bankDTO = new BankDTO();
        bankDTO.setBankName(bank1.getBankName());
        bankDTO.setBankAddress(bank1.getBankAddress());

        Set<Branch> branches = bank1.getBranchSet();
        if(!CollectionUtils.isEmpty(branches)) {
            List<BranchDTO> branchDTOS = branches.stream().map(branch -> {
                BranchDTO branchDTO = new BranchDTO();
                branchDTO.setBranchName(branch.getBranchName());
                branchDTO.setBranchAddress(branch.getBranchAddress());
                return branchDTO;
            }).collect(Collectors.toList());
            bankDTO.setBranches(branchDTOS);

        }

        return  CompletableFuture.completedFuture(bankDTO);
    }

    @Async
    @Cacheable("byBankName")
    public CompletableFuture<BankDTO> findbyBankName(String bankName) throws BankNotFoundException, InterruptedException {
        Thread.sleep(6000);
        log.info("Find the bank by name:{}", bankName);

        Optional<Bank> bank = bankRepository.findByBankName(bankName);

        if(bank.isEmpty()){
            log.error("Bank details are not found for the bank name:{}", bankName);
            throw new BankNotFoundException("Bank details not found");
        }
        Bank bank1 = bank.get();
        BankDTO bankDTO = new BankDTO();
        bankDTO.setBankName(bank1.getBankName());
        bankDTO.setBankAddress(bank1.getBankAddress());

        Set<Branch> branches = bank1.getBranchSet();
        if(!CollectionUtils.isEmpty(branches)) {
            List<BranchDTO> branchDTOS = branches.stream().map(branch -> {
                BranchDTO branchDTO = new BranchDTO();
                branchDTO.setBranchName(branch.getBranchName());
                branchDTO.setBranchAddress(branch.getBranchAddress());
                return branchDTO;
            }).collect(Collectors.toList());
            bankDTO.setBranches(branchDTOS);
        }

        return CompletableFuture.completedFuture(bankDTO);
    }

    @CacheEvict(value = {"byBankCode","byBankName"},allEntries = true)
    public void clearCache(){

    }
}
