package com.itkonboarding.airport_gate.dto.request;

import com.itkonboarding.airport_gate.entities.Airport;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

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
