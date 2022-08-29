package com.itkonboarding.airport_gate.mappers;

import com.itkonboarding.airport_gate.dto.request.AirportRequestDto;
import com.itkonboarding.airport_gate.dto.response.AirportResponseDto;
import com.itkonboarding.airport_gate.entities.Airport;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

/**
 * Mapper used for AirportRequestDto, AirportResponseDto and Airport entity
 */
@Mapper(componentModel = "spring", imports = Collectors.class)
public interface AirportMapper {

    AirportResponseDto airportToAirportResponseDto(Airport airport);

    Airport airportRequestDtoToAirport(AirportRequestDto airportRequestDto);
}
