package com.bankingmanagement.controller;


import com.bankingmanagement.entity.Loan;
import com.bankingmanagement.exceptions.CustomerNotFoundException;
import com.bankingmanagement.exceptions.LoanNotFoundException;
import com.bankingmanagement.model.CustomerDTO;
import com.bankingmanagement.model.CustomerRequest;
import com.bankingmanagement.model.LoanDTO;
import com.bankingmanagement.model.LoanRequest;
import com.bankingmanagement.service.LoanService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @GetMapping
    public ResponseEntity<List<LoanDTO>> findAllLoans(){
        List<LoanDTO> loanDTOS = null;

        try {
            loanDTOS = loanService.findAllLoans();
        } catch (LoanNotFoundException e) {
          log.error("No loan found in DB");
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e ){
            log.error("Unhandled Exception Occurred. Please try again.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(loanDTOS,HttpStatus.OK);
    }

    @GetMapping("byType")
    public ResponseEntity<List<LoanDTO>> findAllByLoanType(@RequestParam @NotNull String loanType){
        List<LoanDTO> loanDTOS = null;
        try {
            loanDTOS = loanService.findAllByLoanType(loanType);
        } catch (LoanNotFoundException e) {
            log.error("No loan found in DB");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e ){
            log.error("Unhandled Exception Occurred. Please try again.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(loanDTOS,HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<LoanDTO> addNewCustomer(@RequestBody @Valid LoanRequest loanRequest) {

        LoanDTO loanDTO;
        try {
            loanDTO = loanService.addNewLoan(loanRequest);
        } catch (LoanNotFoundException e) {
            log.error("No Loan added in DB");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Unhandled Exception occurred. Please try later: {}", e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(loanDTO, HttpStatus.OK);
    }

    @DeleteMapping("{loanId}")
    public ResponseEntity<HttpStatus> deleteCustomerByPhNumber(@PathVariable(name="loanId") int loanId){
        try {
            loanService.deleteLoan(loanId);
        } catch (LoanNotFoundException e) {
            log.error("Loan to be deleted not found in DB");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Unhandled Exception occurred. Please try later" + e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
