package com.itkonboarding.airport_gate.dto;

import lombok.Data;

@Data
public class FlightDto {

    private Integer id;

    private String flightIndex;

    private GateDto gate;
}
