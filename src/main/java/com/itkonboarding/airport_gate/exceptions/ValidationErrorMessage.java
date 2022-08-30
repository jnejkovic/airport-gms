package com.itkonboarding.airport_gate.exceptions;

import lombok.Data;

import java.util.List;

/**
 * Class representing validation error messages
 */
@Data
public class ValidationErrorMessage extends HttpErrorMessage{

    private List<ValidationFieldError> validationMessage;
}
