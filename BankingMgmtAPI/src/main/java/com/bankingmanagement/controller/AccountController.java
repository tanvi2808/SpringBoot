package com.bankingmanagement.controller;


import com.bankingmanagement.exceptions.AccountNotFoundException;
import com.bankingmanagement.model.AccountDTO;
import com.bankingmanagement.model.AccountRequest;
import com.bankingmanagement.repository.AccountRepository;
import com.bankingmanagement.service.AccountService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountDTO>> findAllAccounts(){
        List<AccountDTO> accountDTOS = null;
        try {
            log.info("Inside Account Controller : findAllAccounts");
            accountDTOS= accountService.findAllAccounts();
        }catch(AccountNotFoundException anfe){
            log.error("No Account was found in the Database");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            log.error("Unexpected error was found. Please try later");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(accountDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AccountDTO> addNewAccount(@RequestBody @Valid AccountRequest accountRequest) {

        AccountDTO accountDTO;
        try {
            accountDTO = accountService.addNewAccount(accountRequest);
        } catch (AccountNotFoundException e) {
            log.error("No Account was found in the Database");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Unexpected error was found. Please try later");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<AccountDTO> updateAccount(@RequestBody @Valid AccountRequest accountRequest) {

        AccountDTO accountDTO;
        try {
            accountDTO = accountService.updateAccount(accountRequest);
        } catch (AccountNotFoundException e) {
            log.error("No Account was found in the Database");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Unexpected error was found. Please try later");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{accNumber}")
    public ResponseEntity<HttpStatus> deleteAccountByNumber(@PathVariable(value = "accNumber") int accNumber){

        try {
            accountService.deleteAccount(accNumber);
        } catch (AccountNotFoundException e) {
            log.error("Account to be deleted does not exist in the DB");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Unexpected error was found. Please try later");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
