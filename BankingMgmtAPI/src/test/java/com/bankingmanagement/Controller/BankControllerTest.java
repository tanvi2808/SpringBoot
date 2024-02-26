package com.bankingmanagement.Controller;


import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.exceptions.BankNotFoundException;
import com.bankingmanagement.model.BankDTO;
import com.bankingmanagement.model.BankRequest;
import com.bankingmanagement.service.BankService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BankControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BankService bankService;

    @Test
    public void getAllBanks_whenBankExists_ReturnAll() throws Exception {

        BankDTO bank = new BankDTO();;
        bank.setBankName("ABC");
        bank.setBankAddress("Delhi");
        bank.setBranches(new ArrayList<>());

        List<BankDTO> bankList = new ArrayList<>();

        bankList.add(bank);

        when(bankService.findAllBanks()).thenReturn(bankList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/banks")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());

    }


    @Test
    public void getAllBanks_whenNoBankPreset_thenReturnNotFoundStatus() throws Exception {

        when(bankService.findAllBanks()).thenThrow(new BankNotFoundException());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/banks")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isNotFound());

    }

    @Test
    public void getAllBanks_whenSomeError_thenReturnInternalServerError() throws Exception {

        when(bankService.findAllBanks()).thenThrow(new RuntimeException());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/banks")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isInternalServerError());

    }

    @Test
    public void getBankByCode_whenBankExists_ReturnAll() throws Exception {

        BankDTO bank = new BankDTO();;
        bank.setBankName("ABC");
        bank.setBankAddress("Delhi");
        bank.setBranches(new ArrayList<>());


        when(bankService.findBankById(anyInt())).thenReturn(bank);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/banks/1")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());

    }


    @Test
    public void getBankByCode_whenNoBankPreset_thenReturnNotFoundStatus() throws Exception {

        when(bankService.findBankById(anyInt())).thenThrow(new BankNotFoundException());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/banks/1")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isNotFound());

    }

    @Test
    public void getBankByCode_whenSomeError_thenReturnInternalServerError() throws Exception {

        when(bankService.findBankById(anyInt())).thenThrow(new RuntimeException());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/banks/1")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isInternalServerError());

    }

    @Test
    public void getBankByName_whenBankExists_ReturnBank() throws Exception {

        BankDTO bank = new BankDTO();;
        bank.setBankName("ABC");
        bank.setBankAddress("Delhi");
        bank.setBranches(new ArrayList<>());


        when(bankService.findByBankName(anyString())).thenReturn(bank);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/banks/name?name=SBI")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());

    }


    @Test
    public void getBankByName_whenNoBankPreset_thenReturnNotFoundStatus() throws Exception {

        when(bankService.findByBankName(anyString())).thenThrow(new BankNotFoundException());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/banks/name?name=SBI")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isNotFound());

    }

    @Test
    public void getBank_whenSomeError_thenReturnInternalServerError() throws Exception {

        when(bankService.findByBankName(anyString())).thenThrow(new RuntimeException());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/banks/name?name=SBI")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isInternalServerError());

    }

    @Test
    public void getBankByNameAndCode_whenBankExists_thenReturnStatusOK() throws Exception {
        BankDTO bankDTO = new BankDTO();
        bankDTO.setBankName("SBI");
        bankDTO.setBankAddress("Delhi");

        when(bankService.findByBankName(anyString())).thenReturn(bankDTO);
        when(bankService.findBankById(anyInt())).thenReturn(bankDTO);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/banks//byCodeAndName?code=1&name=SBI")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());

    }

    @Test
    public void saveBank_whenValidBankDetails_thenReturnSavedBank() throws Exception {
        BankDTO bankDTO = new BankDTO();
        bankDTO.setBankName("SBI");
        bankDTO.setBankAddress("Delhi");

        BankRequest bankRequest = new BankRequest();
        bankRequest.setBankName("ICICI");
        bankRequest.setBankAddress("Delhi");

        when(bankService.addNewBank(any())).thenReturn(bankDTO);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/banks")
                .content(asJsonString(bankRequest))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());


    }

    @Test
    public void updateBank_whenValidBankDetails_thenReturnUpdatedBank() throws Exception {
        BankDTO bankDTO = new BankDTO();
        bankDTO.setBankName("SBI");
        bankDTO.setBankAddress("Delhi");

        BankRequest bankRequest = new BankRequest();
        bankRequest.setBankName("ICICI");
        bankRequest.setBankAddress("Delhi");

        when(bankService.updateBank(any())).thenReturn(bankDTO);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/v1/banks")
                .content(asJsonString(bankRequest))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());


    }

    @Test
    public void deleteBank_whenValidBankId_thenDeleteBank() throws Exception {

        when(bankService.deleteBank(anyInt())).thenReturn("Deleted Successfully");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1/banks?code=234")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void deleteBank_whenInvalidBankId_thenReturnNotFound() throws Exception {

        when(bankService.deleteBank(anyInt())).thenThrow(new BankNotFoundException());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1/banks?code=234")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isNotFound());
    }

    @Test
    public void deleteBankByName_whenValidBBankName_thenDeleteBank() throws Exception {
        when(bankService.deleteBankByName(anyString())).thenReturn("Deleted Successfully");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1/banks/byName?name=SBI")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void deleteBankByName_whenInvalidBankName_thenReturnNotFound() throws Exception {
        when(bankService.deleteBankByName(anyString())).thenThrow(new BankNotFoundException());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1/banks/byName?name=TEST")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isNotFound());
    }

    @Test
    public void deleteBankByName_whenOtherError_thenReturnInternalServerError() throws Exception {
        when(bankService.deleteBankByName(anyString())).thenThrow(new RuntimeException());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1/banks/byName?name=TEST")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isInternalServerError());
    }


    public String asJsonString(Object object)  {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(object);
            return jsonString;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
