package com.itkonboarding.airport_gate.dto.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Airport Dto used for requests
 *
 * @author jnejkovic
 */
@Data
public class AirportRequestDto {

    private Integer id;

    private String airportName;

    private List<GateRequestDto> gates = new ArrayList<>();
}
