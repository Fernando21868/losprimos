package com.example.backend.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(){
        super("User y/o contraseña incorrecto");
    }
}
