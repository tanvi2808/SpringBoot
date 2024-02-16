package com.bankingmanagement.service;

import com.bankingmanagement.exceptions.BankNotFoundException;
import com.bankingmanagement.model.BankDTO;

import java.util.concurrent.CompletableFuture;

public interface AsyncBankService {

    public CompletableFuture<BankDTO> findBankDetails(int code) throws InterruptedException, BankNotFoundException;
    public CompletableFuture<BankDTO> findbyBankName(String bankName) throws BankNotFoundException, InterruptedException;

    public void clearCache();
}
