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

    /**
     * Function to handle internal errors
     * @param exception the exception that occur
     * @return response to verify the error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({InternalException.class})
    public ResponseEntity<?> internalException(RuntimeException exception){
        ResponseErrorDto responseErrorDto = new ResponseErrorDto(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), true);
        return new ResponseEntity<>(responseErrorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Function to handle not found errors
     * @param exception the exception that occur
     * @return response to verify the error
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({PatientNotFoundException.class, EmployeeNotFoundException.class})
    public ResponseEntity<?> notFoundException(RuntimeException exception){
        ResponseErrorDto responseErrorDto = new ResponseErrorDto(null, HttpStatus.BAD_REQUEST.value(), exception.getMessage(), true);
        return new ResponseEntity<>(responseErrorDto, HttpStatus.BAD_REQUEST);
    }

    /**
     * Function to handle already exist errors
     * @param exception the exception that occur
     * @return response to verify the error
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UsernameAlreadyExistException.class, EmailAlreadExistException.class, DniAlreadyExistException.class})
    public ResponseEntity<?> alreadyFoundException(RuntimeException exception){
        ResponseErrorDto responseErrorDto = new ResponseErrorDto(null, HttpStatus.BAD_REQUEST.value(), exception.getMessage(), true);
        return new ResponseEntity<>(responseErrorDto, HttpStatus.BAD_REQUEST);
    }

    /**
     * Function to handle validations request errors
     * @param exception the exception that occur
     * @return response to verify the error
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
                    if (errors.containsKey(error.getField())) {
                        errors.put(error.getField(), String.format("%s, %s", errors.get(error.getField()), error.getDefaultMessage()));
                    } else {
                        errors.put(error.getField(), error.getDefaultMessage());
                    }
                }
        );
        ResponseErrorDto responseErrorDto = new ResponseErrorDto(errors, HttpStatus.BAD_REQUEST.value(), "VALIDATION_FAILED", true);
        return new ResponseEntity<>(responseErrorDto, HttpStatus.BAD_REQUEST);
    }

}
