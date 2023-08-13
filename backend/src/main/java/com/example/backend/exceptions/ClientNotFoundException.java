package com.example.backend.exceptions;

public class ClientNotFoundException extends RuntimeException{

    public ClientNotFoundException(String message) {
        super(message);
    }
}
