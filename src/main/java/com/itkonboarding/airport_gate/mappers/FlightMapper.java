package com.itkonboarding.airport_gate.mappers;

import com.itkonboarding.airport_gate.dto.request.FlightRequestDto;
import com.itkonboarding.airport_gate.dto.request.FlightUpdateRequestDto;
import com.itkonboarding.airport_gate.dto.response.FlightResponseDto;
import com.itkonboarding.airport_gate.entities.Flight;
import org.mapstruct.Mapper;

/**
 * Mapper used for FlightResponseDto, FlightRequestDto and Flight entity
 *
 * @author jnejkovic
 */
@Mapper(componentModel = "spring")
public interface FlightMapper {

    FlightResponseDto flightToFlightResponseDto(Flight flight);

    Flight flightRequestDtoToFlight(FlightRequestDto flightRequestDto);

    Flight flightUpdateRequestDtoToFlight(FlightUpdateRequestDto flightUpdateRequestDto);
}
