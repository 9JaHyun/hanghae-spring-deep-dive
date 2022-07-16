package com.spartanorder.menu.controller;

import com.spartanorder.menu.exception.BadPriceException;
import com.spartanorder.menu.exception.DuplicateMenuName;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.spartanorder.menu.controller")
public class MenuExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> duplicateMenuNamePrice(DuplicateMenuName e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .contentType(MediaType.APPLICATION_JSON)
              .body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> badPriceException(BadPriceException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .contentType(MediaType.APPLICATION_JSON)
              .body(e.getMessage());
    }
}
