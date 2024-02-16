package com.bankingmanagement.service;

import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.entity.Branch;
import com.bankingmanagement.exceptions.BankNotFoundException;
import com.bankingmanagement.exceptions.BranchNotFoundException;
import com.bankingmanagement.model.BranchDTO;
import com.bankingmanagement.model.BranchRequest;
import com.bankingmanagement.repository.BankRepository;
import com.bankingmanagement.repository.BranchRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BranchServiceImplTest {

    @Mock
    BranchRepository branchRepository;

    @Mock
    BankRepository bankRepository;

    @InjectMocks
    BranchServiceImpl branchService;



    public static Branch createBranch(){
        Branch branch = new Branch();
        branch.setBranchName("BR1");
        branch.setBranchAddress("Pune");
        branch.setBranchId(1001);

        Bank bank = new Bank();
        bank.setBankName("SBI");
        bank.setBankCode(1111);
        bank.setBankAddress("Mumbai");

        branch.setBank(bank);

        return branch;

    }

    public static BranchRequest createBranchRequest(){
        BranchRequest branchRequest = new BranchRequest();
        branchRequest.setBranchId(1001);
        branchRequest.setBranchAddress("Pune");
        branchRequest.setBank_code(1111);

        return  branchRequest;

    }

    @Test
    public void findAllBranches_whenBranchExists_thenReturnBranch() throws BranchNotFoundException {

        Branch branch = createBranch();
        List<Branch> branches = new ArrayList<>();
        branches.add(branch);

        when(branchRepository.findAll()).thenReturn(branches);

        List< BranchDTO> branchDTOS = branchService.findAllBranches();

        Assertions.assertEquals(1,branchDTOS.size());

    }

    @Test
    public void findAllBranches_whenBranchDoesExists_thenThrowException() throws BranchNotFoundException {

        Exception exception = assertThrows(BranchNotFoundException.class, () -> {
            branchService.findAllBranches();
        });

    }

    @Test
    public void findBranchByName_whenBranchExists_thenReturnBranch() throws BranchNotFoundException{

        when(branchRepository.findByBranchName(anyString())).thenReturn(Optional.of(createBranch()));

        BranchDTO branchDTO = branchService.findBranchByName("SBI");

        Assertions.assertNotNull(branchDTO);
        Assertions.assertEquals("BR1", branchDTO.getBranchName());
    }


    @Test
    public void findBranchByName_whenBranchDoesExists_thenThrowException() throws BranchNotFoundException {

        Exception exception = assertThrows(BranchNotFoundException.class, () -> {
            branchService.findBranchByName("BR1");
        });

    }

    @Test
    public void addNewBranch_whenBranchSave_thenReturnBranch() throws BranchNotFoundException {

        Bank bank = new Bank();
        bank.setBankCode(111);
        bank.setBankName("TEST");
        bank.setBankAddress("Gurgaon");

        Branch branch = new Branch();
        branch.setBranchId(1001);
        branch.setBranchName("BR1")
        ;

        BranchRequest branchRequest = new BranchRequest();
        branchRequest.setBank_code(111);


        when(branchRepository.save(any())).thenReturn(branch);
        when(bankRepository.findById(anyInt())).thenReturn(Optional.of(bank));

        BranchDTO branchDTO = branchService.addNewBranch(branchRequest);


        Assertions.assertNotNull(branchDTO);

        Assertions.assertEquals("BR1", branchDTO.getBranchName());
    }

    @Test
    public void updateBranch_whenValidData_thenReturnBranch() throws BranchNotFoundException, BankNotFoundException {

        Bank bank = new Bank();
        bank.setBankCode(111);
        bank.setBankName("TEST");
        bank.setBankAddress("Gurgaon");

        Branch branch = new Branch();
        branch.setBranchId(1001);
        branch.setBranchName("BR1")
        ;

        BranchRequest branchRequest = new BranchRequest();
        branchRequest.setBank_code(111);


        when(branchRepository.save(any())).thenReturn(branch);
        when(bankRepository.findById(anyInt())).thenReturn(Optional.of(bank));

        BranchDTO branchDTO = branchService.updateBranch(branchRequest);


          Assertions.assertNotNull(branchDTO);

        Assertions.assertEquals("BR1", branchDTO.getBranchName());
    }

    @Test
    public void deletBranchById_whenBranchExists_thenVerfiyDeleteCall() throws BranchNotFoundException{

      doNothing().when(branchRepository).delete(any());
      when(branchRepository.findById(anyInt())).thenReturn(Optional.of(createBranch()));
      branchService.deleteBranch(234);
      verify(branchRepository,timeout(1)).delete(any());
    }
}
