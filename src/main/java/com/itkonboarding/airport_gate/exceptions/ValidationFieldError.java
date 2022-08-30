package com.itkonboarding.airport_gate.exceptions;

import lombok.Data;

/**
 * Class representing validation field error
 *
 * @author jnejkovic
 */
@Data
public class ValidationFieldError {

    private String field;

    private String defaultMessage;

    private String code;
}
