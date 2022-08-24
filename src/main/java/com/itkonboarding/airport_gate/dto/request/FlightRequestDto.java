package com.itkonboarding.airport_gate.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Flight Dto used for requests
 *
 * @author jnejkovic
 */
@Data
public class FlightRequestDto {

    @NotBlank(message = "Flight index must be provided")
    private String flightIndex;

    private Integer gateId;
}
