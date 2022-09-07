package com.itkonboarding.airport_gate.dto.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Airport Dto used for requests
 *
 * @author jnejkovic
 */

@Data
@Accessors(chain = true)
public class AirportRequestDto {

    @NotBlank(message = "Airport name must be provided")
    @Size(min = 3, max = 20, message = "Airport name must be between {min} and {max} characters long.")
    private String airportName;
}
