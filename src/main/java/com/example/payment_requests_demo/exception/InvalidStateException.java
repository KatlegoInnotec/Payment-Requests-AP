package com.example.payment_requests_demo.exception;

public class InvalidStateException extends RuntimeException{
    public InvalidStateException(String message) {
        super(message);
    }
}
