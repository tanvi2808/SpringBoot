package com.spring.reporting.service;

import com.spring.reporting.exception.BankNotFoundException;
import com.spring.reporting.model.BankDTO;
import com.spring.reporting.model.BankRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service

public class ReportingServiceImpl {

    @Value("${bank.url}")
    String bankUrl;

    @Autowired
   WebClient.Builder webClientBuilder;



   // @LoadBalanced
    public List<BankDTO> getAllBanks() throws BankNotFoundException {

        log.info("Inside Reporting API Service : getAllBanks");
        WebClient webClient = webClientBuilder.baseUrl(bankUrl)
        //WebClient webClient = webClientBuilder.baseUrl("http://localhost:9094/api/v1/banks")
                .defaultHeaders(header -> header.setBasicAuth("admin", "password"))
                .build();


        Flux<BankDTO> bankDTOFlux = webClient.get().retrieve().bodyToFlux(BankDTO.class);
        List<BankDTO> bankDTOS = bankDTOFlux.collectList().block();

        return bankDTOS;
    }

    public Mono<BankDTO> getBankById(int id) throws BankNotFoundException {

        log.info("Inside Reporting API Service : find by id : {}", id);
        WebClient webClient = WebClient.create(bankUrl.concat("/").concat(String.valueOf(id)));
        Mono<BankDTO>
            bankDTOMono = webClient
                   .get()
                   .accept(MediaType.APPLICATION_JSON)
                   .retrieve()
                   .bodyToMono(BankDTO.class);
      //   get the status code and return accordingly
//        AtomicReference<BankDTO> bankDTO = new AtomicReference<>();
//        bankDTOMono.subscribe(bankDTO1 -> {
//            bankDTO.set(bankDTO1);
//            System.out.println(bankDTO.get().getBankName());
//        });

         return bankDTOMono;
    }

    public BankDTO saveANewBank(BankRequest bankRequest) throws BankNotFoundException{
        log.info("Inside Reporting API : save a New Bank with bank name :{}", bankRequest.getBankName());
        WebClient webClient = WebClient.create(bankUrl);
        Mono<BankDTO> bankDTOMono = webClient.post()
                                    .body(Mono.just(bankRequest),BankRequest.class)
                                     .retrieve()
                                     .bodyToMono(BankDTO.class);

        return bankDTOMono.block();

    }

    public BankDTO updateBank(BankRequest bankRequest) throws BankNotFoundException{
        log.info("Inside Reporting API :update the existing bank :{}", bankRequest.getBankName());
        WebClient webClient = WebClient.create(bankUrl);
        Mono<BankDTO> bankDTOMono = webClient.put()
                .body(Mono.just(bankRequest),BankRequest.class)
                .retrieve()
                .bodyToMono(BankDTO.class);

        AtomicReference<BankDTO> bankDTO = new AtomicReference<>();
        bankDTOMono.subscribe(bankDTO::set);

        return bankDTO.get();

    }

    public BankDTO deleteBank(int id) throws BankNotFoundException{
        log.info("Inside Reporting API : delete an existing bank with id :{}", id);
        WebClient webClient = WebClient.create(bankUrl+"/"+id);
        Mono<BankDTO> bankDTOMono = webClient.delete()
                .retrieve()
                .bodyToMono(BankDTO.class);
        return  bankDTOMono.block();

    }
}
