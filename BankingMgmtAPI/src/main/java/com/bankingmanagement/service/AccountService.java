package com.bankingmanagement.service;

import com.bankingmanagement.exceptions.AccountNotFoundException;
import com.bankingmanagement.model.AccountDTO;
import com.bankingmanagement.model.AccountRequest;
import com.bankingmanagement.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AccountService {

      List<AccountDTO> findAllAccounts() throws AccountNotFoundException;

      AccountDTO addNewAccount(AccountRequest accountRequest) throws AccountNotFoundException;

      AccountDTO updateAccount(AccountRequest accountRequest) throws AccountNotFoundException;

      void deleteAccount(int accountNumber) throws AccountNotFoundException;

      AccountDTO findAccById(int id) throws AccountNotFoundException;




}
