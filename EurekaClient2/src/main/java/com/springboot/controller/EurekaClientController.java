package com.springboot.controller;

import com.springboot.service.ClientTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
@Slf4j
public class EurekaClientController {

    @Autowired
    ClientTestService clientTestService;

    @GetMapping("/")
    public String getMessage(){
        try {
            return clientTestService.getMessage();
        } catch (URISyntaxException e) {
            log.error("UnExcepted Exception");
        }
        return null;
    }


}
