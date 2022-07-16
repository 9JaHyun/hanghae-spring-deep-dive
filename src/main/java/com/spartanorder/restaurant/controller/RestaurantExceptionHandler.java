package com.spartanorder.restaurant.controller;

import com.spartanorder.restaurant.exception.BadDeliveryFeeException;
import com.spartanorder.restaurant.exception.BadOrderPriceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.spartanorder.restaurant.controller")
public class RestaurantExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> badOrderMinPrice(BadOrderPriceException e) {
        return ResponseEntity
              .status(HttpStatus.BAD_REQUEST)
              .contentType(MediaType.APPLICATION_JSON)
              .body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> badDeliveryPrice(BadDeliveryFeeException e) {
        return ResponseEntity
              .status(HttpStatus.BAD_REQUEST)
              .contentType(MediaType.APPLICATION_JSON)
              .body(e.getMessage());
    }
}
