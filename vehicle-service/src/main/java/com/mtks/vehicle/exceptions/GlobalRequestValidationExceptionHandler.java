package com.mtks.vehicle.exceptions;

import com.mtks.vehicle.dto.response.DefaultErrorResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class GlobalRequestValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<DefaultErrorResponseBody> handleValidationExceptions(MethodArgumentNotValidException ex) {
        DefaultErrorResponseBody errorBody = new DefaultErrorResponseBody("fail","Something went wrong", "validation_error");

        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        if(!errors.isEmpty()){
            errorBody.setMessage(errors.get(0).getDefaultMessage());
        }

        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

}