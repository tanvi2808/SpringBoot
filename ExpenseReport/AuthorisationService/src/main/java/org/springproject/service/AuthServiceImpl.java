package org.springproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springproject.constants.LoggingConstants;
import org.springproject.data.repo.AppUserRepository;
import org.springproject.model.SignUpRequest;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AppUserRepository appUserRepository;
    private final String classname = "AuthServiceImpl";
    @Override
    public String signUp(SignUpRequest signUpRequest) {
        log.info(LoggingConstants.START_INFO_CONSTANT, classname, "signup");
        if(appUserRepository.existsEmail(signUpRequest.getEmail())
            throw new UserExistsException();


        // Add the user to the DB

        //Save it

        // Create Access Token

        // Return access token


        // add him to DB.
        return null;
    }
}
