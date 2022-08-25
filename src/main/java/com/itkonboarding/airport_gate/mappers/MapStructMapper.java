package com.itkonboarding.airport_gate.mappers;

import com.itkonboarding.airport_gate.dto.request.*;
import com.itkonboarding.airport_gate.dto.response.AirportResponseDto;
import com.itkonboarding.airport_gate.dto.response.FlightResponseDto;
import com.itkonboarding.airport_gate.dto.response.GateResponseDto;
import com.itkonboarding.airport_gate.entities.Airport;
import com.itkonboarding.airport_gate.entities.Flight;
import com.itkonboarding.airport_gate.entities.Gate;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper( componentModel="spring")
public interface MapStructMapper {

    AirportResponseDto airportToAirportResponseDto (Airport airport);

    List<AirportResponseDto> airportsToAirportResponseDtos (List <Airport> airports);

    FlightResponseDto flightToFlightResponseDto (Flight flight);

    List<FlightResponseDto> flightsToFlightResponseDtos (List <Flight> flights);

    GateResponseDto gateToGateResponseDto (Gate gate);

    List<GateResponseDto> gatesToGateResponseDtos (List <Gate> gates);
}
