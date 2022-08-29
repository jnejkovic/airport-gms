package com.itkonboarding.airport_gate.mappers;

import com.itkonboarding.airport_gate.dto.request.FlightRequestDto;
import com.itkonboarding.airport_gate.dto.request.FlightUpdateRequestDto;
import com.itkonboarding.airport_gate.dto.response.FlightResponseDto;
import com.itkonboarding.airport_gate.entities.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.stream.Collectors;

/**
 * Mapper used for FlightResponseDto, FlightRequestDto and Flight entity
 *
 * @author jnejkovic
 */
@Mapper(componentModel = "spring", imports = Collectors.class)
public interface FlightMapper {

    @Mapping(target = "gateName", source = "gate.gateName")
    FlightResponseDto flightToFlightResponseDto(Flight flight);

    Flight flightRequestDtoToFlight(FlightRequestDto flightRequestDto);

    @Mapping(target = "gate", ignore = true)
    Flight flightUpdateRequestDtoToFlight(FlightUpdateRequestDto flightUpdateRequestDto);
}
