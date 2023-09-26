package com.example.backend.exceptions;

public class NurseNotFoundException extends EmployeeNotFoundException{
    public NurseNotFoundException(String message) {
        super(message);
    }

}
