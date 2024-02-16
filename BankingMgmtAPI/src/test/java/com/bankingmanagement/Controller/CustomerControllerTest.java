package com.bankingmanagement.Controller;

import com.bankingmanagement.entity.Customer;
import com.bankingmanagement.exceptions.CustomerNotFoundException;
import com.bankingmanagement.model.CustomerDTO;
import com.bankingmanagement.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.wavefront.WavefrontProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.RequestMatcher;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerService customerService;

    @Test
    public void getAllCus_whenCustomerExists_thenReturnCustomer() throws Exception {
        List<CustomerDTO> customerDTOList = new ArrayList();

        CustomerDTO customer = new CustomerDTO();
        customer.setCustName("Test1");

        customer.setCustAddress("Delhi");
        customer.setCustPhone(999999999L);

        customerDTOList.add(customer);

        when(customerService.findAllCustomer()).thenReturn(customerDTOList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void getAllCus_whenCustomerNotExists_thenThrowException() throws Exception {

        when(customerService.findAllCustomer()).thenThrow(new CustomerNotFoundException());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isNotFound());
    }

    @Test
    public void getAllCus_whenServerError_thenThrowException() throws Exception {

        when(customerService.findAllCustomer()).thenThrow(new RuntimeException());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isInternalServerError());
    }

    @Test
    public void getAllCusByPhone_whenCustomerExists_thenStatusOK() throws Exception {


        CustomerDTO customer = new CustomerDTO();
        customer.setCustName("Test1");
        customer.setCustAddress("Delhi");
        customer.setCustPhone(999999999L);

        when(customerService.findByPhoneNumber(anyLong())).thenReturn(customer);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/customers/byPhone?number=1234")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void getAllCusByPhone_whenCustomerNotExists_thenStatusNotFound() throws Exception {


        CustomerDTO customer = new CustomerDTO();
        customer.setCustName("Test1");
        customer.setCustAddress("Delhi");
        customer.setCustPhone(999999999L);

        when(customerService.findByPhoneNumber(anyLong())).thenThrow(new CustomerNotFoundException());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/customers/byPhone?number=23432432")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isNotFound());
    }
}
