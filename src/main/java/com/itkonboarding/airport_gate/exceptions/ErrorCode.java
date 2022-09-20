package com.itkonboarding.airport_gate.exceptions;

/**
 * Class representing error codes
 *
 * @author jnejkovic
 */
public class ErrorCode {

    //airport
    public static final String AIRPORT_NOT_FOUND = "airport.not.found";

    //flight
    public static final String FLIGHT_NOT_FOUND = "flight.not.found";

    //gate
    public static final String GATE_NOT_FOUND = "gate.not.found";
    public static final String GATE_NOT_AVAILABLE = "gate.not.available";

    public static final String GATE_CURRENT_NOT_AVAILABLE = "gate.current.not.available";

    //validation error message
    public static final String VALIDATION_ERROR = "validation.error";
}
