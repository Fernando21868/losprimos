package com.example.backend.exceptions;

public class EmployeeNotFoundException extends UserNotFoundException{

    public EmployeeNotFoundException(String message) {
        super(message);
    }

}
