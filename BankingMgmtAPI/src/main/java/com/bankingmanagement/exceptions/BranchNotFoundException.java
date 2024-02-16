package com.bankingmanagement.exceptions;

public class BranchNotFoundException extends Exception {
    public BranchNotFoundException() {
        super();
    }

    public BranchNotFoundException(String message) {
        super(message);
    }
}
