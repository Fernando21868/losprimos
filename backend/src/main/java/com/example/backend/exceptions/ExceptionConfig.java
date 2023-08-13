package com.example.backend.exceptions;

import com.example.backend.dto.response.ResponseErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionConfig {

    @ExceptionHandler({ClientNotFoundException.class, EmployeeNotFoundException.class})
    public ResponseEntity<?> notFoundException(RuntimeException exception){
        ResponseErrorDto responseErrorDto = new ResponseErrorDto(404, exception.getMessage());
        return new ResponseEntity<>(responseErrorDto, HttpStatus.NOT_FOUND);
    }

}
