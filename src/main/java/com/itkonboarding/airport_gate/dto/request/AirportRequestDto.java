package com.itkonboarding.airport_gate.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Airport Dto used for requests
 *
 * @author jnejkovic
 */
@Data
public class AirportRequestDto {

    @NotBlank(message = "Airport name must be provided")
    private String airportName;
}
