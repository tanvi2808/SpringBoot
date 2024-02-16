package com.bankingmanagement.controller;

import com.bankingmanagement.exceptions.BankNotFoundException;
import com.bankingmanagement.exceptions.BranchNotFoundException;
import com.bankingmanagement.model.BranchDTO;
import com.bankingmanagement.model.BranchRequest;
import com.bankingmanagement.service.BranchService;
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
@RequestMapping("/api/v1/branches")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @GetMapping
    public ResponseEntity<List<BranchDTO>> findAllBranches() throws BranchNotFoundException {
        List<BranchDTO> branchDTOS = null;
        log.info("Inside Branch Controller : findAllBranches");

            branchDTOS = branchService.findAllBranches();

        return new ResponseEntity<>(branchDTOS, HttpStatus.OK);
    }

    @GetMapping("/byName")
    public ResponseEntity<BranchDTO> findByBranchName(@RequestParam @NotNull String branchName) throws BranchNotFoundException {
        BranchDTO branchDTO = null;

            branchDTO = branchService.findBranchByName(branchName);


        return  new ResponseEntity<>(branchDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BranchDTO> addNewBranch(@RequestBody @Valid BranchRequest branchRequest) throws BranchNotFoundException {
        BranchDTO branchDTO = null;

            branchDTO  = branchService.addNewBranch(branchRequest);


        return  new ResponseEntity<>(branchDTO, HttpStatus.OK);

    }


    @PutMapping
    public ResponseEntity<BranchDTO> updateBranch(@RequestBody @Valid BranchRequest branchRequest) throws BranchNotFoundException, BankNotFoundException {
        BranchDTO branchDTO = null;

            branchDTO  = branchService.updateBranch(branchRequest);


        return  new ResponseEntity<>(branchDTO, HttpStatus.OK);

    }

    @DeleteMapping("/{branchId}")
    public ResponseEntity<HttpStatus> deleteBranch(@PathVariable(value = "branchId") int branchId) throws BranchNotFoundException {

            branchService.deleteBranch(branchId);


        return  new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/byName")
    public ResponseEntity<HttpStatus> deleteBranchByName(@RequestParam(value = "branchName") String branchName) throws BranchNotFoundException {

            branchService.deleteBranchByName(branchName);

        return  new ResponseEntity<>(HttpStatus.OK);

    }


}
