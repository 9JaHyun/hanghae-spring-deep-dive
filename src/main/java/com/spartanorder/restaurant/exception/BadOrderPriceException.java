package com.spartanorder.restaurant.exception;

public class BadOrderPriceException extends RuntimeException {

    public BadOrderPriceException(String message) {
        super(message);
    }
}
