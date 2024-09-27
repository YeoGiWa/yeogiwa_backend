package com.example.yeogiwa.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    protected ResponseEntity<?> handleHttpException(HttpClientErrorException e) {
        return ResponseEntity.status(e.getStatusCode()).body(null);
    }
}
