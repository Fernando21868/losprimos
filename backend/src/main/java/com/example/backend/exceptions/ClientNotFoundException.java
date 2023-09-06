package com.example.backend.exceptions;

public class ClientNotFoundException extends UserNotFoundException{
    public ClientNotFoundException(String message) {
        super(message);
    }

}
