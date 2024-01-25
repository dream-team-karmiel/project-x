package com.karmiel.backoffice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<String> notFoundHandler (ResourceNotFoundException e){
        String errorMessage = e.getMessage();
        log.error(errorMessage);
        return ResponseEntity.status(404).body(e.getMessage());

    }
}
