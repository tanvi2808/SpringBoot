package com.bankingmanagement.service;

import com.bankingmanagement.exceptions.LoanNotFoundException;
import com.bankingmanagement.model.LoanDTO;
import com.bankingmanagement.model.LoanRequest;

import java.util.List;

public interface LoanService {

     List<LoanDTO> findAllLoans() throws LoanNotFoundException;
     List<LoanDTO> findAllByLoanType(String type) throws LoanNotFoundException;

     LoanDTO addNewLoan(LoanRequest loanRequest) throws LoanNotFoundException;

     void deleteLoan(int loanId) throws LoanNotFoundException;



}
