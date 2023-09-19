package com.example.backend.exceptions;

public class UserNotFoundException extends PersonNotFoundException {

    public UserNotFoundException(String message) {
        super(message);
    }

}
