package com.example.backend.exceptions;

public class PatientNotFoundException extends PersonNotFoundException{
    public PatientNotFoundException(String message) {
        super(message);
    }

}
