package com.itkonboarding.airport_gate.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Airport Dto used for response
 *
 * @author jnejkovic
 */
@Data
public class AirportResponseDto {

    private Integer id;

    private String airportName;

    private List<GateResponseDto> gates = new ArrayList<>();
}
