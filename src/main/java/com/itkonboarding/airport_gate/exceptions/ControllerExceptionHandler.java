package com.itkonboarding.airport_gate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorMessage eObject = new ErrorMessage();
        eObject.setCode(NOT_FOUND.value());
        eObject.setMessage(ex.getMessage());
        eObject.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<ErrorMessage>(eObject, NOT_FOUND);
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<ErrorMessage> handleNoDataFoundException(NoDataFoundException ex) {
        ErrorMessage eObject = new ErrorMessage();
        eObject.setCode(NO_CONTENT.value());
        eObject.setMessage(ex.getMessage());
        eObject.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<ErrorMessage>(eObject, HttpStatus.OK);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        return errorMap;
    }
}
