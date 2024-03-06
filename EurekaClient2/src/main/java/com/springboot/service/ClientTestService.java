package com.springboot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Service
public class ClientTestService {

    @Autowired
    RestTemplate restTemplate;

    public String getMessage() throws URISyntaxException {

        URI url = new URI("http://eureka-client-service-1/message");
        log.info(url.getHost());
        String response = restTemplate.getForObject(url, String.class);

        log.info("The response from the rest template is " + response);
        return response;
    }
}
