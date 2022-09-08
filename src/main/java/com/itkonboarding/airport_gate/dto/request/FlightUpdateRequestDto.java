package com.itkonboarding.airport_gate.dto.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * Dto used for update Flight
 *
 * @author jnejkovic
 */
@Data
@Accessors(chain = true)
public class FlightUpdateRequestDto {

    @Size(min = 2, max = 5, message = "Flight index should be between {min} and {max} characters long.")
    private String flightIndex;

    @Min(value = 1, message = "must be equal or grater than {value}")
    private Integer gateId;
}
