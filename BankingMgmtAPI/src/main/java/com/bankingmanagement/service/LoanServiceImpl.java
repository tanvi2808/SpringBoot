package com.bankingmanagement.service;

import com.bankingmanagement.entity.Branch;
import com.bankingmanagement.entity.Customer;
import com.bankingmanagement.entity.Loan;
import com.bankingmanagement.exceptions.LoanNotFoundException;
import com.bankingmanagement.model.*;
import com.bankingmanagement.repository.BranchRepository;
import com.bankingmanagement.repository.CustomerRepository;
import com.bankingmanagement.repository.LoanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LoanServiceImpl implements LoanService{

    @Autowired
    LoanRepository loanRepository;
    BranchRepository branchRepository;
    CustomerRepository customerRepository;


    @Override
    public List<LoanDTO> findAllLoans() throws LoanNotFoundException {

        List<Loan> loans = loanRepository.findAll();
        if(CollectionUtils.isEmpty(loans))
            throw new LoanNotFoundException();
        List<LoanDTO> loanDTOS = loans.stream().map(loan -> {
            LoanDTO loanDTO = new LoanDTO();
            loanDTO.setLoanType(loan.getLoanType());
            loanDTO.setLoanAmount(loan.getLoanAmount());
            Branch loandBranch = loan.getBranch();
            if(loandBranch!=null) {
                BranchDTO branchDTO = new BranchDTO();
                branchDTO.setBranchName(loandBranch.getBranchName());
                branchDTO.setBranchAddress(loandBranch.getBranchAddress());
                if(loandBranch.getBank()!=null)
                    branchDTO.setBankName(loandBranch.getBank().getBankName());
                loanDTO.setBranchDTO(branchDTO);
            }
            Customer loanCustomer = loan.getCustomer();
            if(loanCustomer!=null){
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setCustName(loanCustomer.getCustName());
                customerDTO.setCustAddress(loanCustomer.getCustAddress());
                loanDTO.setCustomer(customerDTO);

            }

            return loanDTO;
        }).collect(Collectors.toList());

        return loanDTOS;
    }

    @Override
    public List<LoanDTO> findAllByLoanType(String type) throws LoanNotFoundException {
        List<Loan> loans = loanRepository.findByLoanType(type);
        if(loans.isEmpty())
            throw new LoanNotFoundException("No loan found for this type in DB");
        List<LoanDTO> loanDTOS = loans.stream().map(loan -> {
            LoanDTO loanDTO = new LoanDTO();
            loanDTO.setLoanType(loan.getLoanType());
            loanDTO.setLoanAmount(loan.getLoanAmount());
            Branch loanBranch = loan.getBranch();

            if(loanBranch!=null) {
                BranchDTO branchDTO = new BranchDTO();
                branchDTO.setBranchName(loanBranch.getBranchName());
                branchDTO.setBranchAddress(loanBranch.getBranchAddress());
                if(loanBranch.getBank()!=null)
                    branchDTO.setBankName(loanBranch.getBank().getBankName());
                loanDTO.setBranchDTO(branchDTO);
            }
            //BranchDTO branchDTO= new BranchDTO(loanBranch.getBranchName(),loanBranch.getBranchAddress(),loanBranch.getBank().getBankName());
            return loanDTO;

        }).collect(Collectors.toList());
        return loanDTOS;
    }

    @Override
    public LoanDTO addNewLoan(LoanRequest loanRequest) throws LoanNotFoundException {
        log.info("Adding new loan for the customer {} to the branch {}", loanRequest.getCustId(), loanRequest.getBranchId());
        Loan loan = new Loan();
        loan.setLoanType(loanRequest.getLoanType());
        loan.setLoanAmount(loanRequest.getLoanAmount());
        Optional<Branch> loanBranch = branchRepository.findById(loanRequest.getBranchId());
        if(loanBranch.isPresent())
            loan.setBranch(loanBranch.get());

        Optional<Customer> loanCustomer = customerRepository.findById(loanRequest.getCustId());
        if(loanCustomer.isPresent())
            loan.setCustomer(loanCustomer.get());



       Loan newLoan =  loanRepository.save(loan);
        return LoanDTO.mapLoanEntity(newLoan);
    }

    @Override
    public void deleteLoan(int loanId) throws LoanNotFoundException {
        log.info("Deleting the loan with loan id : {} from DB", loanId);
        loanRepository.deleteById(loanId);

    }
}
