package com.itkonboarding.airport_gate.dto.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Flight Dto used for requests
 *
 * @author jnejkovic
 */
@Data
@Accessors(chain = true)
public class FlightRequestDto {

    @NotBlank(message = "Flight index must be provided")
    @Size(min = 2, max = 5, message = "Flight index should be between {min} and {max} characters long.")
    private String flightIndex;
}
