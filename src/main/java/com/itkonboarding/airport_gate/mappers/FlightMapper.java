package com.itkonboarding.airport_gate.mappers;

import com.itkonboarding.airport_gate.dto.request.FlightRequestDto;
import com.itkonboarding.airport_gate.dto.request.FlightUpdateRequestDto;
import com.itkonboarding.airport_gate.dto.response.FlightResponseDto;
import com.itkonboarding.airport_gate.entities.Flight;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper used for FlightResponseDto, FlightRequestDto and Flight entity
 *
 * @author jnejkovic
 */
@Mapper( componentModel="spring")
public interface FlightMapper {

    FlightResponseDto flightToFlightResponseDto (Flight flight);

    List<FlightResponseDto> flightsToFlightResponseDtos (List <Flight> flights);

    Flight flightRequestDtoToFlight (FlightRequestDto flightRequestDto);

    Flight flightUpdateRequestDtoToFlight (FlightUpdateRequestDto flightUpdateRequestDto);

    List <Flight> flightRequestDtosToFlights ( List<FlightRequestDto> flightRequestDtos);
}
