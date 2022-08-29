package com.itkonboarding.airport_gate.mappers;

import com.itkonboarding.airport_gate.dto.request.GateRequestDto;
import com.itkonboarding.airport_gate.dto.request.GateUpdateRequestDto;
import com.itkonboarding.airport_gate.dto.response.GateResponseDto;
import com.itkonboarding.airport_gate.entities.Gate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.stream.Collectors;

/**
 * Mapper used for GateResponseDto, GateRequestDto and Gate entity
 *
 * @author jnejkovic
 */
@Mapper(componentModel = "spring", imports = Collectors.class)
public interface GateMapper {

    @Mapping(target = "airportName", source = "airport.airportName")
    GateResponseDto gateToGateResponseDto(Gate gate);

    @Mapping(target = "airport", ignore = true)
    Gate gateRequestDtoToGate(GateRequestDto gateRequestDto);

    @Mapping(target = "airport", ignore = true)
    Gate gateUpdateRequestDtoToGate(GateUpdateRequestDto gateUpdateRequestDto);
}
