package com.example.backend.exceptions;

public class UserAlreadExistException extends RuntimeException {

    public UserAlreadExistException(String message) {
        super(message);
    }

    public UserAlreadExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadExistException(Throwable cause) {
        super(cause);
    }

    public UserAlreadExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
