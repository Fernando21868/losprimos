package com.example.backend.exceptions;

public class EmailAlreadExistException extends RuntimeException {

    public EmailAlreadExistException(String message) {
        super(message);
    }

    public EmailAlreadExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailAlreadExistException(Throwable cause) {
        super(cause);
    }

    public EmailAlreadExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
