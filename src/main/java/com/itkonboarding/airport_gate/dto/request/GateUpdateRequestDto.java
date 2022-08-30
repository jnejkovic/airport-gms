package com.itkonboarding.airport_gate.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;
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

    @Min(value = 1, message = "must be equal or grater than {value}")
    private Integer airportId;
}
