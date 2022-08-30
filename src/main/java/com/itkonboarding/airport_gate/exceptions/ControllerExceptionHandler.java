package com.itkonboarding.airport_gate.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.List;

import static com.itkonboarding.airport_gate.exceptions.ErrorCode.VALIDATION_ERROR;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<HttpErrorMessage> handleResourceNotFoundException(ResourceNotFoundException ex) {
        HttpErrorMessage eObject = new HttpErrorMessage();
        eObject.setCode(NOT_FOUND.value());
        eObject.setMessage(ex.getMessage());
        eObject.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<HttpErrorMessage>(eObject, NOT_FOUND);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorMessage> handleValidationException(MethodArgumentNotValidException ex) {
        ValidationErrorMessage validationError = new ValidationErrorMessage();
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage()).toList();
        validationError.setCode(BAD_REQUEST.value());
        validationError.setMessage(VALIDATION_ERROR);
        validationError.setTimeStamp(LocalDateTime.now());
        validationError.setValidationMessage(errors);

        return new ResponseEntity<ValidationErrorMessage>(validationError, BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpErrorMessage> handleException(Exception ex) {
        HttpErrorMessage eObject = new HttpErrorMessage();
        eObject.setCode(INTERNAL_SERVER_ERROR.value());
        eObject.setMessage(ex.getMessage());
        eObject.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<HttpErrorMessage>(eObject, INTERNAL_SERVER_ERROR);
    }
}
