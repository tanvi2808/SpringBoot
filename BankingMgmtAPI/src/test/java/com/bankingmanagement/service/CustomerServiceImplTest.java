package com.bankingmanagement.service;

import com.bankingmanagement.entity.Account;
import com.bankingmanagement.entity.Branch;
import com.bankingmanagement.entity.Customer;
import com.bankingmanagement.entity.Loan;
import com.bankingmanagement.exceptions.CustomerNotFoundException;
import com.bankingmanagement.model.CustomerDTO;
import com.bankingmanagement.model.CustomerRequest;
import com.bankingmanagement.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

import java.util.*;
import java.util.concurrent.BlockingDeque;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerServiceImpl customerService;


    public Customer addNewCustomer(){
        Customer customer = new Customer();
        customer.setCustId(111);
        customer.setCustName("Rohit");
        customer.setCustAddress("Noida");
        customer.setCustPhone(88897897788L);

        Branch branch = new Branch();
        branch.setBranchName("BR1");
        branch.setBranchAddress("Noida");

        Account account = new Account();
        account.setAccountType("Savings");
        account.setAccountBalance(123.00);
        account.setAccountNumber(1001);
        account.setBranch(branch);

        Set<Account> accountSet = new HashSet<>();
        accountSet.add(account);

        customer.setAccountSet(accountSet);

        Loan loan = new Loan();
        loan.setLoanType("Housing");
        loan.setLoanAmount(123.67);
        loan.setCustomer(customer);
        loan.setBranch(branch);

        Set<Loan> loans = new HashSet<>();
        loans.add(loan);

        customer.setLoan(loans);

        return customer;

    }

    @Test
    public void findAllCust_whenCustPresent_thenReturnList() throws CustomerNotFoundException {

        List<Customer> customers = new ArrayList<>();
        customers.add(addNewCustomer());
        customers.add(addNewCustomer());

        when(customerRepository.findAll()).thenReturn(customers);

        List<CustomerDTO> customerDTOS = customerService.findAllCustomer();

        Assertions.assertEquals(2, customers.size());
    }

    @Test
    public void findAllCust_whenNoCustPresent_thenThrowException(){
        Exception exception = assertThrows(CustomerNotFoundException.class, () ->{
            customerService.findAllCustomer();
        });
    }

    @Test
    public void findCustByPhone_whenCustPresent_thenReturnCust() throws CustomerNotFoundException {

        Customer customer = addNewCustomer();
        when(customerRepository.findByCustPhone(anyLong())).thenReturn(Optional.of(customer));

        CustomerDTO customer2 = customerService.findByPhoneNumber(123L);

        Assertions.assertEquals("Rohit", customer2.getCustName());
    }


    @Test
    public void findCustByPhone_whenNoCustPresent_thenThrowException(){
        Exception exception = assertThrows(CustomerNotFoundException.class, () ->{
            customerService.findByPhoneNumber(234234L);
        });
    }


    @Test
    public void addNewCust_whenValidData_thenReturnCust() throws CustomerNotFoundException {

        CustomerRequest customerreq = new CustomerRequest();
        customerreq.setCustomerName("Rohit");
        customerreq.setCustomerAddress("Noida");
        customerreq.setCustomerPhone(45343554L);
        when(customerRepository.save(any())).thenReturn(addNewCustomer());

        CustomerDTO newCust = customerService.addNewCustomer(customerreq);

        Assertions.assertEquals("Rohit", newCust.getCustName());
    }

    @Test
    public void updateCust_whenValidData_thenReturnCust() throws CustomerNotFoundException {

        CustomerRequest customerreq = new CustomerRequest();
        customerreq.setCustomerName("Sachin");
        customerreq.setCustomerAddress("Noida");
        customerreq.setCustomerPhone(45343554L);
        customerreq.setCustId(111);

        when(customerRepository.save(any())).thenReturn(addNewCustomer());

        CustomerDTO newCust = customerService.updateCustomer(customerreq);

        Assertions.assertEquals("Rohit", newCust.getCustName());
    }

    @Test
    public void deleteCust_whenValidCustId_verifyDeleteCalled() throws CustomerNotFoundException {
        doNothing().when(customerRepository).deleteById(anyInt());
        when(customerRepository.findById(anyInt())).thenReturn(Optional.of(addNewCustomer()));
        customerService.deleteCustomer(123);

        verify(customerRepository, times(1)).deleteById(anyInt());
    }

    @Test
    public void deleteCustByPhone_whenValidCustId_verifyDeleteCalled() throws CustomerNotFoundException {
        doNothing().when(customerRepository).deleteByCustPhone(anyLong());
        when(customerRepository.findByCustPhone(anyLong())).thenReturn(Optional.of(addNewCustomer()));
        customerService.deleteCustomerByPhoneNumber(123L);

        verify(customerRepository, times(1)).deleteByCustPhone(anyLong());
    }
}
