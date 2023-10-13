package com.example.backend.exceptions;

public class EmailAlreadExistException extends AlreadyExistException {

    public EmailAlreadExistException(String message) {
        super(message);
    }

}
