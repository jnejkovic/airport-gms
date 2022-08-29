package com.itkonboarding.airport_gate.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Gate Dto used for requests
 *
 * @author jnejkovic
 */
@Data
public class GateRequestDto {

    @NotBlank(message = "Gate name must be provided")
    @Size(min = 2, max = 3, message = "Flight index should be between {min} and {max} characters long.")
    private String gateName;

    private Integer airportId;
}
