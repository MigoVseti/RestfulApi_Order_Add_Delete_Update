package com.migran.app.exceptions;

import com.migran.app.model.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<?> orderNotFound(GlobalExceptions exceptions){
        ExceptionHandler exceptionHandler = new ExceptionHandler(LocalDateTime.now(), exceptions.getMessage(), "Order not found");
        return new  ResponseEntity<>(exceptionHandler, HttpStatus.NOT_FOUND);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(LocalDateTime.now(), exception.getMessage(), "The server cannot process the data, check the data for Validity");
        return new ResponseEntity<>(exceptionHandler, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
