package com.itkonboarding.airport_gate.dto.request;

import lombok.Data;

import javax.validation.constraints.Size;

/**
 * Dto used for update flight
 *
 * @author jnejkovic
 */
@Data
public class GateUpdateRequestDto {

    @Size(min = 2, max = 3, message = "Flight index should be between {min} and {max} characters long.")
    private String gateName;

    private Integer airportId;
}
