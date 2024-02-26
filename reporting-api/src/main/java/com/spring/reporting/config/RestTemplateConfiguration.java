package com.spring.reporting.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate getRestTemplate(){
        SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(6000);
        clientHttpRequestFactory.setReadTimeout(12000);

        return new RestTemplate(clientHttpRequestFactory);
    }

}
