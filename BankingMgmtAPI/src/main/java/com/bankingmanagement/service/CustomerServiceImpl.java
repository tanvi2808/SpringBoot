package com.bankingmanagement.service;

import com.bankingmanagement.entity.Account;
import com.bankingmanagement.entity.Branch;
import com.bankingmanagement.entity.Customer;
import com.bankingmanagement.exceptions.CustomerNotFoundException;
import com.bankingmanagement.model.*;
import com.bankingmanagement.repository.CustomerRepository;
import jakarta.transaction.Transactional;
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
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    CustomerRepository customerRepository;




    @Override
    public List<CustomerDTO> findAllCustomer() throws CustomerNotFoundException {

        List<Customer> customers = customerRepository.findAll();
        if(CollectionUtils.isEmpty(customers))
            throw new CustomerNotFoundException();
        List<CustomerDTO> customerDTOS = customers.stream().map(customer -> {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setCustName(customer.getCustName());
            customerDTO.setCustAddress(customer.getCustAddress());
            customerDTO.setCustPhone(customer.getCustPhone());
            if(!CollectionUtils.isEmpty(customer.getAccountSet())){
                log.info("Getting details of customer accounts");
                List<AccountDTO> accountDTOS = customer.getAccountSet().stream().map(account -> {
                    AccountDTO accountDTO = new AccountDTO();
                    accountDTO.setAccountType(account.getAccountType());
                    accountDTO.setAccountBalance(account.getAccountBalance());
                    if(account.getBranch() != null)
                        accountDTO.setBranchName(account.getBranch().getBranchName());
                    accountDTO.setCustomerName(customer.getCustName());
                    return accountDTO;
                }).collect(Collectors.toList());
             customerDTO.setAccounts(accountDTOS);

            }
            if(!CollectionUtils.isEmpty(customer.getLoan())){
                log.info("Getting customer loans");
                List<LoanDTO> loanDTOS = customer.getLoan().stream().map(loan -> {
                    LoanDTO loanDTO = new LoanDTO();
                    loanDTO.setLoanAmount(loan.getLoanAmount());
                    loanDTO.setLoanType(loan.getLoanType());
                    Branch branch = loan.getBranch();
                    BranchDTO branchDTO = null;
                    if(branch!=null){
                        branchDTO = new BranchDTO();
                        branchDTO.setBranchName(branch.getBranchName());
                        if(branch.getBank()!=null)
                            branchDTO.setBankName(branch.getBank().getBankName());
                        branchDTO.setBranchAddress(branch.getBranchAddress());
                    }

                    loanDTO.setBranchDTO(branchDTO);

                    return loanDTO;

                }).collect(Collectors.toList());
                customerDTO.setLoans(loanDTOS);
            }
            return customerDTO;
        }).collect(Collectors.toList());

        return customerDTOS;
    }

    @Override
    public CustomerDTO findByPhoneNumber(Long num) throws CustomerNotFoundException {

        Optional<Customer> optionalCustomer = customerRepository.findByCustPhone(num);
        if(optionalCustomer.isEmpty())
            throw new CustomerNotFoundException();
        Customer customer = optionalCustomer.get();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustName(customer.getCustName());
        customerDTO.setCustAddress(customer.getCustAddress());
        if(!(customer.getAccountSet() ==null)){
            List<AccountDTO> custAccounts =  customer.getAccountSet().stream().map(account -> {
                AccountDTO accountDTO = new AccountDTO();
                accountDTO.setAccountType(account.getAccountType());
                accountDTO.setAccountBalance(account.getAccountBalance());
                if(account.getBranch()!=null)
                    accountDTO.setBranchName(account.getBranch().getBranchName());
                return accountDTO;
            }).collect(Collectors.toList());
            customerDTO.setAccounts(custAccounts);
        }

        return customerDTO;
    }

    @Override
    public CustomerDTO addNewCustomer(CustomerRequest customerRequest) throws CustomerNotFoundException {
        log.info("Add a new customer with customer name = {}",customerRequest.getCustomerName() );
        Customer customer = new Customer();
        customer.setCustPhone(customerRequest.getCustomerPhone());
        customer.setCustAddress(customerRequest.getCustomerAddress());
        customer.setCustName(customerRequest.getCustomerName());

        Customer newCustomer = customerRepository.save(customer);

        if(Objects.isNull(newCustomer))
            throw new CustomerNotFoundException();

        log.info("New Customer with cust id {} added to the db", newCustomer.getCustId());
        return CustomerDTO.mapCustomerEntity(newCustomer);

    }

    @Override
    public CustomerDTO updateCustomer(CustomerRequest customerRequest) throws CustomerNotFoundException {
        Customer customer = new Customer();
        customer.setCustId(customerRequest.getCustId());
        customer.setCustPhone(customerRequest.getCustomerPhone());
        customer.setCustAddress(customerRequest.getCustomerAddress());
        customer.setCustName(customerRequest.getCustomerName());


        Customer newCustomer = customerRepository.save(customer);
        if(Objects.isNull(newCustomer))
            throw new CustomerNotFoundException();
        return CustomerDTO.mapCustomerEntity(newCustomer);
    }

    @Override
    public void deleteCustomer(int custId) throws CustomerNotFoundException {
        log.info("Deleting customer with custId: {}", custId );
        Optional<Customer> customerToBeDeleted = customerRepository.findById(custId);
        if(customerToBeDeleted.isEmpty())
            throw new CustomerNotFoundException("Customer to be deleted is not present in DB");
        customerRepository.deleteById(custId);


    }

    @Override
    @Transactional
    public void deleteCustomerByPhoneNumber(long phNum) throws CustomerNotFoundException {
        log.info("Deleting customer by phone number : {}" , phNum);
        Optional<Customer> customerToBeDeleted = customerRepository.findByCustPhone(phNum);
        if(customerToBeDeleted.isEmpty())
            throw new CustomerNotFoundException("Customer to be deleted is not present in DB");
        customerRepository.deleteByCustPhone(phNum);

    }


}
