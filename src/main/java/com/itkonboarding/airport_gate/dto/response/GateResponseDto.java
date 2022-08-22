package com.itkonboarding.airport_gate.dto.response;

import com.itkonboarding.airport_gate.dto.request.FlightRequestDto;
import com.itkonboarding.airport_gate.entities.Airport;
import com.itkonboarding.airport_gate.entities.Gate;
import com.itkonboarding.airport_gate.enumerations.EStatus;
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

    private Airport airport;

    private List<FlightRequestDto> flights = new ArrayList<>();

    private EStatus status;
}
