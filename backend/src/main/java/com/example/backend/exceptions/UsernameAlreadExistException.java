package com.example.backend.exceptions;

public class UsernameAlreadExistException extends RuntimeException {

    public UsernameAlreadExistException(String message) {
        super(message);
    }

    public UsernameAlreadExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameAlreadExistException(Throwable cause) {
        super(cause);
    }

    public UsernameAlreadExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
