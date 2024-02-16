package com.bankingmanagement.exceptions;

import com.bankingmanagement.service.BankService;

public class BankNotFoundException extends Exception{
    public BankNotFoundException() {
        super();
    }

    public BankNotFoundException(String message) {
        super(message);
    }
}
