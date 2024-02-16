package com.bankingmanagement.controller;


import com.bankingmanagement.exceptions.CustomerNotFoundException;
import com.bankingmanagement.model.CustomerDTO;
import com.bankingmanagement.model.CustomerRequest;
import com.bankingmanagement.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> findAllCustomers() {
        List<CustomerDTO> customerDTOS = null;
        try {
            customerDTOS= customerService.findAllCustomer();
        } catch (CustomerNotFoundException cnfe) {
            log.error("No Customer found in DB");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e ){
            log.error("Unhandled Exception occured. Please try later");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(customerDTOS,HttpStatus.OK);

    }

    @GetMapping("/byPhone")
    public ResponseEntity<CustomerDTO> findByPhone(@RequestParam @NotNull Long number){
        CustomerDTO customerDTO = null;

        try {
            customerDTO = customerService.findByPhoneNumber(number);
        } catch (CustomerNotFoundException e) {
            log.error("No Customer found in DB");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e ){
            log.error("Unhandled Exception occurred. Please try later");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> addNewCustomer(@RequestBody @Valid CustomerRequest customerRequest) {

        CustomerDTO customerDTO;
        try {
            customerDTO = customerService.addNewCustomer(customerRequest);
        } catch (CustomerNotFoundException e) {
            log.error("No Customer added in DB");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Unhandled Exception occurred. Please try later: {}", e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody @Valid CustomerRequest customerRequest) {

        CustomerDTO customerDTO;
        try {
            customerDTO = customerService.updateCustomer(customerRequest);
        } catch (CustomerNotFoundException e) {
            log.error("No Customer added in DB");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Unhandled Exception occurred. Please try later");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{custId}")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable(name="custId") int custId){
        try {
            customerService.deleteCustomer(custId);
        } catch (CustomerNotFoundException e) {
            log.error("Customer to be deleted not foudn in DB");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Unhandled Exception occurred. Please try later");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/byNumber")
    public ResponseEntity<HttpStatus> deleteCustomerByPhNumber(@RequestParam(name="phNumber") long phNumber){
        try {
            customerService.deleteCustomerByPhoneNumber(phNumber);
        } catch (CustomerNotFoundException e) {
            log.error("Customer to be deleted not foudn in DB");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Unhandled Exception occurred. Please try later" + e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
