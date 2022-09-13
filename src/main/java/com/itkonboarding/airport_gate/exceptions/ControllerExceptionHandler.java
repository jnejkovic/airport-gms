package com.itkonboarding.airport_gate.exceptions;

import com.itkonboarding.airport_gate.mappers.ValidationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.itkonboarding.airport_gate.exceptions.ErrorCode.VALIDATION_ERROR;
import static org.springframework.http.HttpStatus.*;

/**
 * Class responsible for exception handling
 *
 * @author jnejkovic
 */
@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ControllerExceptionHandler {

    private final ValidationMapper validationMapper;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<HttpErrorMessage> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.error("Handling resource not found exception", ex);

        var errorMessage = new HttpErrorMessage();
        errorMessage.setCode(NOT_FOUND.value());
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<HttpErrorMessage>(errorMessage, NOT_FOUND);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorMessage> handleValidationException(MethodArgumentNotValidException ex) {
        log.error("Handling validation exception", ex);

        var validationError = new ValidationErrorMessage();
        List<ValidationFieldError> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(validationMapper::errorToValidationFieldError).collect(Collectors.toList());
        validationError.setCode(BAD_REQUEST.value());
        validationError.setMessage(VALIDATION_ERROR);
        validationError.setTimeStamp(LocalDateTime.now());
        validationError.setValidationMessage(errors);

        return new ResponseEntity<ValidationErrorMessage>(validationError, BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpErrorMessage> handleException(Exception ex) {
        log.error("Handling exception", ex);

        var errorMessage = new HttpErrorMessage();
        errorMessage.setCode(INTERNAL_SERVER_ERROR.value());
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<HttpErrorMessage>(errorMessage, INTERNAL_SERVER_ERROR);
    }
}
