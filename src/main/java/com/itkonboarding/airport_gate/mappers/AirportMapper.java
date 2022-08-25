package com.itkonboarding.airport_gate.mappers;

import com.itkonboarding.airport_gate.dto.request.AirportRequestDto;
import com.itkonboarding.airport_gate.dto.response.AirportResponseDto;
import com.itkonboarding.airport_gate.entities.Airport;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper used for AirportRequestDto, AirportResponseDto and Airport entity
 */
@Mapper( componentModel="spring")
public interface AirportMapper {

    AirportResponseDto airportToAirportResponseDto (Airport airport);

    List<AirportResponseDto> airportsToAirportResponseDtos (List <Airport> airports);

    Airport airportRequestDtoToAirport (AirportRequestDto airportRequestDto);

    List<Airport> airportRequestDtosToAirports (List<AirportRequestDto> airportRequestDtos);
}
