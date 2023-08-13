package com.example.backend.exceptions;

public class EmployeeNotFoundException extends RuntimeException{

    public EmployeeNotFoundException(String message) {
        super(message);
    }

}
