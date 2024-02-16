package com.bankingmanagement.service;


import com.bankingmanagement.entity.Customer;
import com.bankingmanagement.exceptions.CustomerNotFoundException;
import com.bankingmanagement.model.CustomerDTO;
import com.bankingmanagement.model.CustomerRequest;
import com.bankingmanagement.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CustomerService {

     List<CustomerDTO> findAllCustomer() throws CustomerNotFoundException;
     CustomerDTO findByPhoneNumber(Long num) throws CustomerNotFoundException;

     CustomerDTO addNewCustomer(CustomerRequest customerRequest) throws CustomerNotFoundException;
     CustomerDTO updateCustomer(CustomerRequest customerRequest) throws CustomerNotFoundException;

     void deleteCustomer(int custId) throws CustomerNotFoundException;
     void deleteCustomerByPhoneNumber(long phNum) throws CustomerNotFoundException;



}
