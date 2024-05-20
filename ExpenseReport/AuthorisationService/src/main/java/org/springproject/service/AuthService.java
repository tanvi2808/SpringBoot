package org.springproject.service;

import org.springproject.model.SignUpRequest;

public interface AuthService {

    String signUp(SignUpRequest signUpRequest);
}
