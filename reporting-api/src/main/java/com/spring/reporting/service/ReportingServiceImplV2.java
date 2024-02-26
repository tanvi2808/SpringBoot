package com.spring.reporting.service;

import com.spring.reporting.exception.BankNotFoundException;
import com.spring.reporting.model.BankDTO;
import com.spring.reporting.model.BankRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.wavefront.WavefrontProperties;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Service
@Slf4j
@EnableRetry
public class ReportingServiceImplV2 {

    @Value(("${bank.url}"))
    String bankUrl;

    @Autowired
    RestTemplate restTemplate;

//    private String credentials(){
//        String credential = "user:password";
//            return Base64.getEncoder().encodeToString(credential.getBytes());
//    }
    private HttpHeaders httpHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("user","password");
      //  headers.add("Authorization", "Basic "+credentials());
        return headers;
    }

    @Retryable(value={Exception.class}, maxAttempts = 5, backoff = @Backoff(delay = 5000))
    @
    public List<BankDTO> getAllBanks() throws URISyntaxException, BankNotFoundException {
        log.info("Inside Reporting Service v2 : getAllBanks ");
        URI uri = new URI(bankUrl);
        log.info("uri = " + uri);
        ResponseEntity<BankDTO[]> response =   restTemplate.exchange(uri,HttpMethod.GET,new HttpEntity<>(httpHeaders()), BankDTO[].class);
        log.info("The status returned from the call is " + response.getStatusCode());
        return Arrays.asList(response.getBody());

    }

    @Retryable(value={Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 3000))

    public BankDTO getBankById(int code ) throws BankNotFoundException, URISyntaxException {
        log.info("Inside Reporting Service V2 : getBankByCode : {} ", code);
        URI uri = new URI(bankUrl+"/"+code);
        log.info("The URI is " + uri);

        ResponseEntity<BankDTO> response = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(httpHeaders()), BankDTO.class);
        log.info("The status returned from the call is " + response.getStatusCode());
        return response.getBody();

    }

   public BankDTO addNewBank(BankRequest bankRequest) throws URISyntaxException, BankNotFoundException {

        log.info("Inside Reporting Controller v2 : add new bank with bank name:{}", bankRequest.getBankName());
        URI uri = new URI(bankUrl);


        HttpEntity<BankRequest> entity = new HttpEntity<>(bankRequest, httpHeaders());
        ResponseEntity<BankDTO> response = restTemplate.exchange(uri,HttpMethod.POST, entity, BankDTO.class);
       log.info("The status of the returned request is : " + response.getStatusCode());

       return response.getBody();


   }

    public void updateBank(BankRequest bankRequest) throws BankNotFoundException, URISyntaxException {
        log.info("Inside Reporting Service V2 :  update Bank with code : {} ", bankRequest.getBankCode());
        URI uri = new URI(bankUrl);
        restTemplate.put(uri, bankRequest);

    }

    public void deleteBank(int bankCode) throws URISyntaxException {
        log.info("Inside Reporting Service V2 : delete bank with code : {} ",bankCode);
        URI uri = new URI(bankUrl+"?code="+bankCode);
        restTemplate.delete(uri);
    }

    public void deleteBankByName( @NotNull String  name) throws URISyntaxException {
        log.info("Inside Reporting Service V2 : delete bank with name : {} ",name);
        URI uri = new URI(bankUrl+"/byName?name="+name);
        restTemplate.delete(uri);
    }

    @Recover
    public List<BankDTO> recover(Exception e){
        System.out.println("in the recover method with exception " + e.getLocalizedMessage());
        return null;
    }

    @Recover
    public BankDTO recoverById(Exception e){
        System.out.println("in the ID recover method with exception " + e.getLocalizedMessage());
        return null;
    }
}
