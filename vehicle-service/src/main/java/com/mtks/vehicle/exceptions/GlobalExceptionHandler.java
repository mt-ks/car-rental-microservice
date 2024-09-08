package com.mtks.vehicle.exceptions;

import com.mtks.vehicle.dto.response.DefaultErrorResponseBody;
import com.mtks.vehicle.exceptions.api.CustomMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException{

    @ExceptionHandler({Exception.class,RuntimeException.class, HttpMessageNotReadableException.class, AuthenticationException.class,AuthenticationCredentialsNotFoundException.class})
    public ResponseEntity<DefaultErrorResponseBody> handleException(Exception e) {
        DefaultErrorResponseBody error = new DefaultErrorResponseBody();

        if(e instanceof HttpMessageNotReadableException) {
            error.setMessage("There was a problem with your request.");
        }

        if(e instanceof CustomMessageException){
            error.setMessage(e.getMessage());
        }

        if(e instanceof AuthenticationException){
            error.setMessage("There was a problem with your request.");
        }

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
