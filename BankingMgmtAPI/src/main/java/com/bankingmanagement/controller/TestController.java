package com.bankingmanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String home(){
        return new String("<h1>Welcome</h1>");
    }

    @GetMapping("/users")
    public String user(){
        return new String("<h1>Welcome User</h1>");
    }

    @GetMapping("/admin")
    public String admin(){
        return new String("<h1>Welcome Admin</h1>");
    }

}
