package com.itkonboarding.airport_gate.exceptions;

/**
 * Exception thrown when resource is not found
 *
 * @author jnejkovic
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
