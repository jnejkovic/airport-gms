package com.itkonboarding.airport_gate.dto.request;

import lombok.Data;

/**
 * Flight Dto used for requests
 *
 * @author jnejkovic
 */
@Data
public class FlightRequestDto {

    private Integer id;

    private String flightIndex;

    private GateRequestDto gate;
}
