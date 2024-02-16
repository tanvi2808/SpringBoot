package com.bankingmanagement.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionalHandlerAdvice {

    @ExceptionHandler(BranchNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleBranchException(BranchNotFoundException bnfe, HttpServletRequest httpRequest){
        log.error("There was {} exception encountered", bnfe.getLocalizedMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorMessage(bnfe.getLocalizedMessage());
        exceptionResponse.setRequestedURIID(httpRequest.getRequestURI());
        return exceptionResponse;
    }

    @ExceptionHandler(BankNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleBankNotFoundException(BankNotFoundException bnfe, HttpServletRequest request){
        log.error("There was {} exception encountered", bnfe.getLocalizedMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorMessage(bnfe.getMessage());
        exceptionResponse.setRequestedURIID(request.getRequestURI());
        return exceptionResponse;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleGenericException(Exception ex, HttpServletRequest request){
        ExceptionResponse exResponse = new ExceptionResponse();
        exResponse.setErrorMessage(ex.getMessage());
        exResponse.setRequestedURIID(request.getRequestURI());
        return exResponse;
    }
}
