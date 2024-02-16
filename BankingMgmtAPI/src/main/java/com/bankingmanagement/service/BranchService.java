package com.bankingmanagement.service;

import com.bankingmanagement.exceptions.BankNotFoundException;
import com.bankingmanagement.exceptions.BranchNotFoundException;
import com.bankingmanagement.model.BranchDTO;
import com.bankingmanagement.model.BranchRequest;

import java.util.List;

public interface BranchService {
     List<BranchDTO> findAllBranches() throws BranchNotFoundException;

     BranchDTO findBranchByName(String bname) throws BranchNotFoundException;

     BranchDTO addNewBranch(BranchRequest branchRequest) throws BranchNotFoundException;

     BranchDTO updateBranch(BranchRequest branchRequest) throws BranchNotFoundException, BankNotFoundException;

     void deleteBranch(int branchId) throws BranchNotFoundException;

    void deleteBranchByName(String branchName) throws BranchNotFoundException;

}
