package com.itkonboarding.airport_gate.dto;

import com.itkonboarding.airport_gate.entities.Airport;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GateDto {

    private Integer id;

    private String gateName;

    private Airport airport;

    private List<FlightDto> flights = new ArrayList<>();

    private Boolean status;
}
