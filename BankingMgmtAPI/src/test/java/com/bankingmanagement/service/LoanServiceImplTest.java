package com.bankingmanagement.service;

import com.bankingmanagement.entity.Branch;
import com.bankingmanagement.entity.Customer;
import com.bankingmanagement.entity.Loan;
import com.bankingmanagement.exceptions.CustomerNotFoundException;
import com.bankingmanagement.exceptions.LoanNotFoundException;
import com.bankingmanagement.model.CustomerDTO;
import com.bankingmanagement.model.CustomerRequest;
import com.bankingmanagement.model.LoanDTO;
import com.bankingmanagement.model.LoanRequest;
import com.bankingmanagement.repository.BranchRepository;
import com.bankingmanagement.repository.CustomerRepository;
import com.bankingmanagement.repository.LoanRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class LoanServiceImplTest {

    @Mock
    LoanRepository loanRepository;

    @Mock
    BranchRepository branchRepository;

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    LoanServiceImpl loanService;

    @Test
    public void findAll_whenLoanExists_thenReturnLoans() throws LoanNotFoundException {
        List<Loan> loans = new ArrayList<>();
        Loan loan = new Loan();
        loan.setLoanType("Personal");
        loan.setLoanAmount(234d);

        Branch branch = new Branch();
        branch.setBranchId(1001);
        branch.setBranchName("BR1");

        loan.setBranch(branch);

        Customer customer = new Customer();
        customer.setCustId(111);
        customer.setCustName("Rohit");
        customer.setCustAddress("Noida");
        customer.setCustPhone(88897897788L);

        loan.setCustomer(customer);

        loans.add(loan);

        when(loanRepository.findAll()).thenReturn(loans);

        List<LoanDTO> loanDTOS = loanService.findAllLoans();

        Assertions.assertFalse(loanDTOS.isEmpty());
    }

    @Test
    public void findAllLoan_whenNoLoanPresent_thenThrowException(){
        Exception exception = assertThrows(LoanNotFoundException.class, () ->{
            loanService.findAllLoans();
        });
    }

    @Test
    public void findByLoanType_whenLoanExists_thenReturnLoans() throws LoanNotFoundException {
        List<Loan> loans = new ArrayList<>();
        Loan loan = new Loan();
        loan.setLoanType("Personal");
        loan.setLoanAmount(234d);

        Branch branch = new Branch();
        branch.setBranchId(1001);
        branch.setBranchName("BR1");

        loan.setBranch(branch);

        Customer customer = new Customer();
        customer.setCustId(111);
        customer.setCustName("Rohit");
        customer.setCustAddress("Noida");
        customer.setCustPhone(88897897788L);

        loan.setCustomer(customer);

        loans.add(loan);

        when(loanRepository.findByLoanType(anyString())).thenReturn(loans);

        List<LoanDTO> loanDTOS = loanService.findAllByLoanType("Savings");

        Assertions.assertFalse(loanDTOS.isEmpty());
    }
    @Test
    public void findLoanByLoanType_whenNoLoanPresent_thenThrowException(){
        Exception exception = assertThrows(LoanNotFoundException.class, () ->{
            loanService.findAllByLoanType("Car");
        });
    }

    @Test
    public void addNewLoan_whenValidData_thenReturnLoan() throws LoanNotFoundException {
        Loan loan = new Loan();
        loan.setLoanType("Personal");
        loan.setLoanAmount(234d);

        Branch branch = new Branch();
        branch.setBranchId(1001);
        branch.setBranchName("BR1");

        loan.setBranch(branch);

        Customer customer = new Customer();
        customer.setCustId(111);
        customer.setCustName("Rohit");
        customer.setCustAddress("Noida");
        customer.setCustPhone(88897897788L);

        loan.setCustomer(customer);

        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setLoanAmount(234.34);
        loanRequest.setLoanType("Car");
      ;
        when(loanRepository.save(any())).thenReturn(loan);
        when(branchRepository.findById(anyInt())).thenReturn(Optional.of(branch));
        when(customerRepository.findById(anyInt())).thenReturn(Optional.of(customer));

        LoanDTO loanDTO = loanService.addNewLoan(loanRequest);

        Assertions.assertEquals("Personal", loanDTO.getLoanType());
    }

    @Test
    public void deleteLoan_whenValidLoanId_verifyDeleteCalled() throws CustomerNotFoundException, LoanNotFoundException {
        Loan loan = new Loan();
        loan.setLoanType("Personal");
        loan.setLoanAmount(234d);
        loan.setLoanId(123);

        doNothing().when(loanRepository).deleteById(anyInt());
        loanService.deleteLoan(123);

        verify(loanRepository, times(1)).deleteById(anyInt());
    }
}
