package org.springproject.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springproject.constants.LoggingConstants;
import org.springproject.dto.AuthRequest;
import org.springproject.dto.AuthResponse;
import org.springproject.mapper.AuthRequestMapper;
import org.springproject.mapper.AuthRequestMapperImpl;
import org.springproject.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

   private final String classname = "AuthController";

   @Autowired
   private AuthService authService;

    @PostMapping("/sign-up")
    ResponseEntity<AuthResponse> signUp(@RequestBody AuthRequest authRequest) {
       String methodName = "signUp";

       log.info(LoggingConstants.START_INFO_CONSTANT, classname, methodName, authRequest);
        String accessToken = authService.signUp(AuthRequestMapper.INSTANCE.mapToSignUp(authRequest));

        log.info(LoggingConstants.END_INFO_CONSTANT, classname,methodName);
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(accessToken));
    }
}
