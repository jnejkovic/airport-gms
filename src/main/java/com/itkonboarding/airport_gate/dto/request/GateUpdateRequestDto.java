package com.itkonboarding.airport_gate.dto.request;

import lombok.Data;

/**
 * Dto used for update flight
 *
 * @author jnejkovic
 */
@Data
public class GateUpdateRequestDto {

    private String gateName;

    private Integer airportId;
}
