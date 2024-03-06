package com.spring.reporting.controller;

import com.spring.reporting.exception.BankNotFoundException;
import com.spring.reporting.model.BankDTO;
import com.spring.reporting.model.BankRequest;
import com.spring.reporting.service.ReportingServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/bankReporting")

public class ReportingController {

    @Autowired
    ReportingServiceImpl reportingService;

    @GetMapping
    public ResponseEntity<List<BankDTO>> getAllBanks(){

        log.info("Inside Reporting Controller : getAllBanks");
        List<BankDTO> bankDTOList;
        try {
            bankDTOList = reportingService.getAllBanks();
        } catch (BankNotFoundException e) {
            log.error("No Bank found in the DB");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            log.error("Internal Server Error" + e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return new ResponseEntity<>(bankDTOList, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Mono<BankDTO>> getBankById(@PathVariable("userId") int userId){

        log.info("Inside Reporting Controller : getBankById");
       Mono<BankDTO> bankDTO;
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
    public ResponseEntity<BankDTO> addNewBank(BankRequest bankRequest){
        BankDTO bankDTO;
        try {
           bankDTO= reportingService.saveANewBank(bankRequest);

        } catch (BankNotFoundException e) {
            log.error("No Bank found in the DB");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            log.error("Internal Server Error");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  new ResponseEntity<>(bankDTO, HttpStatus.OK);
    }
}
