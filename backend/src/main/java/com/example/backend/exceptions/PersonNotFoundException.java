package com.example.backend.exceptions;

public class PersonNotFoundException extends NotFoundException {
    public PersonNotFoundException(String message){
        super(message);
    }

}
