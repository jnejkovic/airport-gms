package com.itkonboarding.airport_gate.dto.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalTime;

/**
 * Dto used for update flight
 *
 * @author jnejkovic
 */
@Data
@Accessors(chain = true)
public class GateUpdateRequestDto {

    @Size(min = 2, max = 3, message = "Gate name should be between {min} and {max} characters long.")
    private String gateName;

    private LocalTime availableFrom;

    private LocalTime availableTo;

    @Min(value = 1, message = "must be equal or grater than {value}")
    private Integer airportId;
}
