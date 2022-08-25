package com.itkonboarding.airport_gate.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Gate Dto used for requests
 *
 * @author jnejkovic
 */
@Data
public class GateRequestDto {

    @NotBlank(message = "Gate name must be provided")
    private String gateName;

    @NotBlank(message = "Airport id must be provided")
    private Integer airportId;
}
