package com.example.backend.exceptions;

public class UsernameAlreadyExistException extends AlreadyExistException {

    public UsernameAlreadyExistException(String message) {
        super(message);
    }

}
