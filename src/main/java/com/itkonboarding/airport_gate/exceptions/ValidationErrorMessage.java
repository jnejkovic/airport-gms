package com.itkonboarding.airport_gate.exceptions;

import lombok.Data;

import java.util.List;

@Data
public class ValidationErrorMessage extends HttpErrorMessage{

    private List<String> validationMessage;
}
