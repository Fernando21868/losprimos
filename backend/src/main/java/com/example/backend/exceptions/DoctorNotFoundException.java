package com.example.backend.exceptions;

public class DoctorNotFoundException extends EmployeeNotFoundException{
    public DoctorNotFoundException(String message) {
        super(message);
    }

}
