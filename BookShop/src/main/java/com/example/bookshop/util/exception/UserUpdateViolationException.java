package com.example.bookshop.util.exception;

public class UserUpdateViolationException extends RuntimeException {
    public UserUpdateViolationException(String message) {
        super(message);
    }
}
