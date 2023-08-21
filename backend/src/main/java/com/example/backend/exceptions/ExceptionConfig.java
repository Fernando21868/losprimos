package com.example.backend.exceptions;

import com.example.backend.dto.response.ResponseErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionConfig {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ClientNotFoundException.class, EmployeeNotFoundException.class})
    public ResponseEntity<?> notFoundException(RuntimeException exception){
        ResponseErrorDto responseErrorDto = new ResponseErrorDto(null, 404, exception.getMessage(), true);
        return new ResponseEntity<>(responseErrorDto, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UsernameAlreadExistException.class, EmailAlreadExistException.class})
    public ResponseEntity<?> usernameEmailFoundException(RuntimeException exception){
        ResponseErrorDto responseErrorDto = new ResponseErrorDto(null, 404, exception.getMessage(), true);
        return new ResponseEntity<>(responseErrorDto, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseErrorDto handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
                    if (errors.containsKey(error.getField())) {
                        errors.put(error.getField(), String.format("%s, %s", errors.get(error.getField()), error.getDefaultMessage()));
                    } else {
                        errors.put(error.getField(), error.getDefaultMessage());
                    }
                }
        );
        return new ResponseErrorDto(errors, 404, "VALIDATION_FAILED", true);
    }

}
