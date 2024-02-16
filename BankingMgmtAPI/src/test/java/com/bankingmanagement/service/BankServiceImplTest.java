package com.bankingmanagement.service;

import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.entity.Branch;
import com.bankingmanagement.exceptions.BankNotFoundException;
import com.bankingmanagement.model.BankDTO;
import com.bankingmanagement.model.BankRequest;
import com.bankingmanagement.repository.BankRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class BankServiceImplTest {

    @Mock
    private BankRepository bankRepository;

    @InjectMocks
    private BankServiceImpl bankService;

    @Test
    public void testFindAll() throws BankNotFoundException {

        Bank bank = new Bank();
        bank.setBankCode(111);
        bank.setBankName("Test");
        bank.setBankAddress("Gurugram");
        List<Bank> banks = new ArrayList<>();
        banks.add(bank);

        when(bankRepository.findAll()).thenReturn(banks);

        List<BankDTO> bankDTOS = bankService.findAllBanks();

        assertEquals(1, bankDTOS.size());

    }

    @Test
    public void testFindAllWithBranch() throws BankNotFoundException {
        Bank bank = new Bank();
        bank.setBankCode(111);
        bank.setBankName("Test");
        bank.setBankAddress("Gurugram");
        Branch branch = new Branch();
        branch.setBranchId(101);
        branch.setBranchName("New Branch");
        branch.setBranchAddress("Mexico");
        branch.setBank(bank);

        Set<Branch> branches = new HashSet<>();
        branches.add(branch);

        bank.setBranchSet(branches);
        List<Bank> banks = new ArrayList<>();
        banks.add(bank);

        when((bankRepository).findAll()).thenReturn(banks);

        assertEquals(1, bankService.findAllBanks().size());

    }

    @Test
    public void testFindBankDetails_whenValidId_thenReturnsBank() throws InterruptedException, BankNotFoundException {

        Bank bank = new Bank();
        bank.setBankCode(111);
        bank.setBankName("Test");
        bank.setBankAddress("Gurugram");
        Branch branch = new Branch();
        branch.setBranchName("Test-BR1");
        Set<Branch> branches = new HashSet<>();
        branches.add(branch);

        bank.setBranchSet(branches);

        when(bankRepository.findById(anyInt())).thenReturn(Optional.of(bank));

        BankDTO bankDTO = bankService.findBankById(999);
        assertEquals("Test", bank.getBankName());
        assertEquals("Gurugram", bank.getBankAddress());


    }

    @Test
    public void findBankWithNoData() {

        List<Bank> banks = null;
        when(bankRepository.findAll()).thenReturn(banks);

        Exception exception = assertThrows(BankNotFoundException.class, () -> {
            List<BankDTO> bankDTOs = bankService.findAllBanks();

        });

    }

    @Test
    public void testFindBank_whenInvalidId_thenThrowsException() {

        Exception exception = assertThrows(BankNotFoundException.class, () -> {
            BankDTO bankDTO = bankService.findBankById(976);

        });
    }

    @Test
    public void testFindByBankName() throws InterruptedException, BankNotFoundException {
        Bank bank = new Bank();
        bank.setBankCode(111);
        bank.setBankName("Test");
        bank.setBankAddress("Gurugram");

        Branch branch = new Branch();
        branch.setBranchId(101);
        branch.setBranchName("New Branch");
        branch.setBranchAddress("Mexico");
        branch.setBank(bank);

        Set<Branch> branches = new HashSet<>();
        branches.add(branch);

        bank.setBranchSet(branches);

        when(bankRepository.findByBankName(anyString())).thenReturn(Optional.of(bank));

        BankDTO bankDTO = bankService.findByBankName("Gurugram");

        Assertions.assertEquals("Test", bankDTO.getBankName());

    }

    @Test
    public void testFindBank_whenInvalidName_thenThrowsException() {

        Exception exception = assertThrows(BankNotFoundException.class, () -> {
            BankDTO bankDTO = bankService.findByBankName("ABC");

        });

    }

    @Test
    public void testAddNewBank() throws InterruptedException, BankNotFoundException {
        Bank bank = new Bank();
        bank.setBankCode(111);
        bank.setBankName("Test");
        bank.setBankAddress("Gurugram");

        when(bankRepository.save(any())).thenReturn(bank);

        BankRequest bankRequest = new BankRequest();
        bankRequest.setBankName("Test");
        bankRequest.setBankAddress("Gurugram");

        BankDTO bankDTO = bankService.addNewBank(bankRequest);

        Assertions.assertEquals("Test", bankDTO.getBankName());

    }

    @Test
    public void testUpdateNewBank() throws InterruptedException, BankNotFoundException {
        Bank bank = new Bank();
        bank.setBankCode(111);
        bank.setBankName("Test");
        bank.setBankAddress("Gurugram");

        when(bankRepository.save(isA(Bank.class))).thenReturn(bank);

        BankRequest bankRequest = new BankRequest();
        bankRequest.setBankCode(111);
        bankRequest.setBankName("Test2");
        bankRequest.setBankAddress("Gurugram");

        BankDTO bankDTO = bankService.updateBank(bankRequest);

        Assertions.assertEquals("Test", bankDTO.getBankName());

    }

    @Test
    public void testDeleteBankById() throws InterruptedException, BankNotFoundException {


        doNothing().when(bankRepository).deleteById(anyInt());
        bankService.deleteBank(1);
        verify(bankRepository, times(1)).deleteById(anyInt());

        //Assertions.assertEquals("Test", bankDTO.getBankName());

    }

    @Test
    public void testDeleteBankByBankName() throws InterruptedException, BankNotFoundException {

        Bank bank = new Bank();
        bank.setBankName("ABC");

        when(bankRepository.findByBankName(anyString())).thenReturn(Optional.of(bank));
        doNothing().when(bankRepository).deleteByBankName(anyString());
        bankService.deleteBankByName("ABC");
        verify(bankRepository, times(1)).deleteByBankName(anyString());

        //Assertions.assertEquals("Test", bankDTO.getBankName());

    }

    @Test
    public void testDeleteBank_whenInvalidBankName_thenThrowsException() {
        Exception exception = assertThrows(BankNotFoundException.class, () -> {
            bankService.deleteBankByName("TEST123");
        });
    }

}
