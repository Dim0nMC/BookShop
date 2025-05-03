package com.example.bookshop.util.exception;

public class UserDeleteViolationException extends RuntimeException {
    public UserDeleteViolationException(String message) {
        super(message);
    }
}
