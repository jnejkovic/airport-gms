package com.itkonboarding.airport_gate.dto.response;

import com.itkonboarding.airport_gate.dto.request.GateRequestDto;
import lombok.Data;

/**
 * Flight Dto used for response
 *
 * @author jnejkovic
 */
@Data
public class FlightResponseDto {

    private Integer id;

    private String flightIndex;

    private GateRequestDto gate;
}
