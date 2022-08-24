package com.itkonboarding.airport_gate.dto.request;

import lombok.Data;

/**
 * Dto used for update Flight
 *
 * @author jnejkovic
 */
@Data
public class FlightUpdateRequestDto {

    private String flightIndex;

    private Integer gateId;
}
