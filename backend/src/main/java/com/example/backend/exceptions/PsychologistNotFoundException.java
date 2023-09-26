package com.example.backend.exceptions;

public class PsychologistNotFoundException extends EmployeeNotFoundException{
    public PsychologistNotFoundException(String message) {
        super(message);
    }

}
