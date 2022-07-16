package com.spartanorder.order.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> illArgumentHandler(IllegalArgumentException e) {
        return ResponseEntity
              .status(HttpStatus.BAD_REQUEST)
              .contentType(MediaType.APPLICATION_JSON)
              .body(e.getMessage());
    }

}
