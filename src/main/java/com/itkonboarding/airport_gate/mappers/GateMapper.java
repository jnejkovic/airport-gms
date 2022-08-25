package com.itkonboarding.airport_gate.mappers;

import com.itkonboarding.airport_gate.dto.request.GateRequestDto;
import com.itkonboarding.airport_gate.dto.request.GateUpdateRequestDto;
import com.itkonboarding.airport_gate.dto.response.GateResponseDto;
import com.itkonboarding.airport_gate.entities.Gate;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper used for GateResponseDto, GateRequestDto and Gate entity
 *
 * @author jnejkovic
 */
@Mapper( componentModel="spring")
public interface GateMapper {

    GateResponseDto gateToGateResponseDto (Gate gate);

    List<GateResponseDto> gatesToGateResponseDtos (List <Gate> gates);

    Gate gateRequestDtoToGate (GateRequestDto gateRequestDto);

    Gate gateUpdateRequestDtoToGate (GateUpdateRequestDto gateUpdateRequestDto);

    List<Gate> gateRequestDtosToGates (List<GateRequestDto> gateRequestDtos);
}
