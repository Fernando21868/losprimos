package com.example.backend.exceptions;

public class VerificationCodeNotFoundException extends NotFoundException{

    public VerificationCodeNotFoundException(String message) {
        super(message);
    }

}
