package com.itkonboarding.airport_gate.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AirportDto {

    private Integer id;

    private String airportName;

    private List<GateDto> gates = new ArrayList<>();
}
