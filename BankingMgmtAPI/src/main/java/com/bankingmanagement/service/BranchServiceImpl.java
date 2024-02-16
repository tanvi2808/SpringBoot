package com.bankingmanagement.service;

import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.entity.Branch;
import com.bankingmanagement.exceptions.BankNotFoundException;
import com.bankingmanagement.exceptions.BranchNotFoundException;
import com.bankingmanagement.model.BranchDTO;
import com.bankingmanagement.model.BranchRequest;
import com.bankingmanagement.repository.BankRepository;
import com.bankingmanagement.repository.BranchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BranchServiceImpl implements BranchService{

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    BankRepository bankRepository;

    @Override
    public List<BranchDTO> findAllBranches() throws BranchNotFoundException {
        List<BranchDTO> branchDTOS = null;
        List<Branch> branches = branchRepository.findAll();
        if(CollectionUtils.isEmpty(branches))
            throw new BranchNotFoundException();
        branchDTOS = branches.stream().map(branch -> {
            BranchDTO branchDTO = new BranchDTO();
            branchDTO.setBranchName(branch.getBranchName());
            branchDTO.setBranchAddress(branch.getBranchAddress());
            if(branch.getBank()!=null)
                branchDTO.setBankName(branch.getBank().getBankName());
            return branchDTO;

        }).collect(Collectors.toList());


        return branchDTOS;
    }

    @Override
    public BranchDTO findBranchByName(String branchName) throws BranchNotFoundException {
        Optional<Branch> branchOpt = branchRepository.findByBranchName( branchName);
        if(branchOpt.isEmpty())
            throw new BranchNotFoundException("Branch Not Found");
        BranchDTO branchDTO = new BranchDTO();
        Branch branch = branchOpt.get();
        branchDTO.setBranchName(branch.getBranchName());
        branchDTO.setBranchAddress(branch.getBranchAddress());
        branchDTO.setBankName(branch.getBank().getBankName());

        return branchDTO;
    }

    @Override
    public BranchDTO addNewBranch(BranchRequest branchRequest) throws BranchNotFoundException {

        Branch branch = new Branch();
        branch.setBranchName(branchRequest.getBranchName());
        branch.setBranchAddress(branchRequest.getBranchAddress());

        Bank branchBank = null;

        Optional<Bank> bankOptional = bankRepository.findById(branchRequest.getBank_code());

        if(!bankOptional.isEmpty())
            branchBank = bankOptional.get();

        branch.setBank(branchBank);

        Branch branch1 = branchRepository.save(branch);

        if(Objects.isNull(branch1))
            throw new BranchNotFoundException("No Branch updated in DB");

        BranchDTO branchDTO = new BranchDTO();
        branchDTO.setBranchName(branch1.getBranchName());
        branchDTO.setBranchAddress(branch1.getBranchAddress());
        if(branch1.getBank()!=null)
            branchDTO.setBankName(branch1.getBank().getBankName());


        return branchDTO;
    }

    @Override
    public BranchDTO updateBranch(BranchRequest branchRequest) throws BranchNotFoundException, BankNotFoundException {
        Branch branch = new Branch();
        branch.setBranchName(branchRequest.getBranchName());
        branch.setBranchAddress(branchRequest.getBranchAddress());
        branch.setBranchId(branchRequest.getBranchId());



        Optional<Bank> bankOptional = bankRepository.findById(branchRequest.getBank_code());

        if(bankOptional.isEmpty())
            throw new BankNotFoundException("Corresponding bank could not be found");

        Bank branchBank = bankOptional.get();

        branch.setBank(branchBank);

        Branch branch1 = branchRepository.save(branch);

        if(Objects.isNull(branch1))
            throw new BranchNotFoundException("No Branch updated in DB");

        BranchDTO branchDTO = new BranchDTO();
        branchDTO.setBranchName(branch1.getBranchName());
        branchDTO.setBranchAddress(branch1.getBranchAddress());
        if(branch1.getBank()!=null)
            branchDTO.setBankName(branch1.getBank().getBankName());

        return branchDTO;
    }

    public void deleteBranch(int branchId) throws BranchNotFoundException{
        log.error("Deleting the branch with id: {}", branchId);
        Optional<Branch> branchToBeDeleted = branchRepository.findById(branchId);
        if(branchToBeDeleted.isEmpty())
            throw new BranchNotFoundException("Branch to be deleted not found in DB");
        branchRepository.delete(branchToBeDeleted.get());

    }

    public void deleteBranchByName(String branchName) throws BranchNotFoundException{
        log.error("Deleting the branch with name: {}", branchName);
        Optional<Branch> branchToBeDeleted = branchRepository.findByBranchName(branchName);
        if(branchToBeDeleted.isEmpty())
            throw new BranchNotFoundException("Branch to be deleted not found in DB");
        branchRepository.deleteByBranchName(branchName);

    }
}
