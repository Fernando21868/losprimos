package com.example.backend.exceptions;

public class AdministratorNotFoundException extends EmployeeNotFoundException{
    public AdministratorNotFoundException(String message) {
        super(message);
    }

}
