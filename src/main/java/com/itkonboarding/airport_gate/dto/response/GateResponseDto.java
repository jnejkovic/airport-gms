package com.itkonboarding.airport_gate.dto.response;

import com.itkonboarding.airport_gate.entities.Gate;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Gate Dto used for response
 *
 * @author jnejkovic
 */
@Data
public class GateResponseDto {

    private Integer id;

    private String gateName;

    private String airportName;

    private List<FlightResponseDto> flights = new ArrayList<>();

    private Gate.Status status;
}
