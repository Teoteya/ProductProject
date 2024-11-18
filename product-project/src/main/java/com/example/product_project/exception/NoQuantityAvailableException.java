package com.example.product_project.exception;

public class NoQuantityAvailableException extends RuntimeException {
    public NoQuantityAvailableException(String errorMessage) {
        super(errorMessage);
    }
}