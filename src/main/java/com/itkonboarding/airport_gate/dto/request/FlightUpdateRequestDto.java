package com.itkonboarding.airport_gate.dto.request;

import lombok.Data;

import javax.validation.constraints.Size;

/**
 * Dto used for update Flight
 *
 * @author jnejkovic
 */
@Data
public class FlightUpdateRequestDto {

    @Size(min = 2, max = 5, message = "Flight index should be between {min} and {max} characters long.")
    private String flightIndex;

    private Integer gateId;
}
