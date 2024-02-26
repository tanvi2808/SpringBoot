package com.spring.reporting.controller;

import com.spring.reporting.exception.BankNotFoundException;
import com.spring.reporting.model.BankDTO;
import com.spring.reporting.model.BankRequest;
import com.spring.reporting.service.ReportingServiceImpl;
import com.spring.reporting.service.ReportingServiceImplV2;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v2/bankReporting")
public class ReportingContollerV2 {

    @Autowired
    ReportingServiceImplV2 reportingService;

    @GetMapping
    public ResponseEntity<List<BankDTO>> getAllBanks(){

        log.info("Inside Reporting Controller 2 : getAllBanks");
        List<BankDTO> bankDTOList = null;
        try {
            bankDTOList = reportingService.getAllBanks();
        } catch (BankNotFoundException e) {
            log.error("No Bank found in the DB");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            log.error("Internal Server Error");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return new ResponseEntity<>(bankDTOList, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<BankDTO> getBankById(@PathVariable("userId") int userId){

        log.info("Inside Reporting Controller  2 : getBankById");
        BankDTO bankDTO   = null;
        try {
            bankDTO = reportingService.getBankById(userId);
        } catch (BankNotFoundException e) {
            log.error("No Bank found in the DB");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            log.error("Internal Server Error");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return new ResponseEntity<>(bankDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BankDTO> addNewBank(@RequestBody BankRequest bankRequest) {

        log.info("Inside Reporting Controller  2 : add new Bank");
        BankDTO bankDTO   = null;
        try {
            bankDTO = reportingService.addNewBank(bankRequest);
        } catch (BankNotFoundException e) {
            log.error("No Bank found in the DB");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            log.error("Internal Server Error");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(bankDTO, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateBank(@RequestBody BankRequest bankRequest) {

        log.info("Inside Reporting Controller  2 : update Bank");
        BankDTO bankDTO   = null;
        try {
             reportingService.updateBank(bankRequest);
        } catch (BankNotFoundException e) {
            log.error("No Bank found in the DB");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            log.error("Internal Server Error");
           return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteBank(@RequestParam("code") int bankCode){
        try {
            reportingService.deleteBank(bankCode);
        }catch (Exception e ){
            log.error("Unknow Exception caught " + e.getLocalizedMessage());
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return  new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/byName")
    public ResponseEntity<HttpStatus> deleteBankByName(@RequestParam("name") String bankName){
        try {
            reportingService.deleteBankByName(bankName);
        }catch (Exception e ){
            log.error("Unknow Exception caught " + e.getLocalizedMessage());
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return  new ResponseEntity<>(HttpStatus.OK);

    }
}
