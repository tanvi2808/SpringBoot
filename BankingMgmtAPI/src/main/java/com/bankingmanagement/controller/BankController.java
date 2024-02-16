package com.bankingmanagement.controller;

import com.bankingmanagement.exceptions.BankNotFoundException;
import com.bankingmanagement.model.BankDTO;
import com.bankingmanagement.model.BankRequest;
import com.bankingmanagement.service.AsyncBankService;
import com.bankingmanagement.service.BankService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/api/v1/banks")
public class BankController {

    @Autowired
    private BankService bankService;
    @Autowired
    private AsyncBankService asyncBankService;

    @GetMapping
    public ResponseEntity<List<BankDTO>> findAllBanks(){
        log.info("Inside BankController : findAllBanks");
        List<BankDTO> bankDTOs = null;
        try {
            bankDTOs = bankService.findAllBanks();
        }catch(BankNotFoundException bnfe){
            log.error("No Bank found in database");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception e1){
            log.error("Error while getting bank details");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Bank Details : " + bankDTOs);

        return new ResponseEntity<>(bankDTOs, HttpStatus.OK);
    }

    @GetMapping("/{code}")
    public ResponseEntity<BankDTO> findBankByCode(@PathVariable("code") @Min(1) int code){
        BankDTO bankDTO = null;
        try {
            bankDTO = bankService.findBankById(code);
        } catch (BankNotFoundException e) {
            log.error("Bank Not Found with code : " + code);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Unhandled Exception caught");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(bankDTO, HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<BankDTO> findBankByName(@RequestParam @NotNull String name){
        BankDTO bankDTO = null;
        try {
            bankDTO = bankService.findByBankName(name);
        } catch (BankNotFoundException e) {
            log.error("Bank Not Found with code : " + name);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Unhandled Exception caught");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(bankDTO, HttpStatus.OK);


    }

    @GetMapping("/byCodeAndName")
    public ResponseEntity<List<BankDTO>> findByCodeAndName(@RequestParam(name="code")int code,
                                                     @RequestParam(name="name") String name) throws BankNotFoundException, InterruptedException {


        BankDTO bankDTO1 = bankService.findBankById(code);
        BankDTO bankDTO2 = bankService.findByBankName(name);

        List<BankDTO> bankDTOS =new ArrayList<>();

        bankDTOS.add(bankDTO1);
        bankDTOS.add(bankDTO2);

        return  new ResponseEntity<>(bankDTOS,HttpStatus.OK);

    }

    @GetMapping("/byNameAndCode")
    public ResponseEntity<List<BankDTO>> findByNameAndCode(@RequestParam(name="name")String name,
                                                     @RequestParam(name="code") int code) throws InterruptedException, BankNotFoundException, ExecutionException {

        CompletableFuture<BankDTO> bankDTO = null;
        CompletableFuture<BankDTO> bankDTO1 = null;
        List<BankDTO> bankDTOS = new ArrayList<>();


            bankDTO = asyncBankService.findbyBankName(name);
            bankDTO1 = asyncBankService.findBankDetails(code);

            CompletableFuture.allOf(bankDTO, bankDTO1).join();
            bankDTOS.add(bankDTO.get());
            bankDTOS.add(bankDTO1.get());

            return new ResponseEntity<>(bankDTOS, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<BankDTO> addNewBank(@RequestBody @Valid  BankRequest bankRequest){
         BankDTO bankDTO = null;

        try{
            bankDTO= bankService.addNewBank(bankRequest);
        }catch(BankNotFoundException bnfe){
            log.error("Bank could not be added to DB");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            log.error("Unhandled Exception caught");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
        return new ResponseEntity<>(bankDTO, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<BankDTO> updateBank(@RequestBody @Valid BankRequest bankRequest){
        BankDTO bankDTO = null;
        try{
            bankDTO = bankService.updateBank(bankRequest);
        } catch (BankNotFoundException e) {
            log.error("Bank could not be added to DB");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }catch (Exception e){
        log.error("Unhandled Exception caught");
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
        return new ResponseEntity<>(bankDTO, HttpStatus.OK);

    }

    @DeleteMapping
    public ResponseEntity<BankDTO> deleteBank(@RequestParam(value = "code") int bankCode){

        try {
            bankService.deleteBank(bankCode);
        } catch (BankNotFoundException e) {
            log.error("Bank could not be deleted from DB");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Unhandled Exception caught");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/byName")
    public ResponseEntity<BankDTO> deleteBankByName(@RequestParam(value = "name") String bankName) throws BankNotFoundException {


            bankService.deleteBankByName(bankName);


        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/clearcache")
    public void clearCache(){
        asyncBankService.clearCache();
    }
}
