package com.itkonboarding.airport_gate.controllers;

import com.itkonboarding.airport_gate.dto.request.AirportRequestDto;
import com.itkonboarding.airport_gate.entities.Airport;
import com.itkonboarding.airport_gate.mappers.AirportMapper;
import com.itkonboarding.airport_gate.services.AirportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AirportControllerTest {

    @Mock
    private AirportMapper airportMapper;

    @Mock
    private AirportService airportService;

    @InjectMocks
    private AirportController airportController;

    @Test
    void create() {
        var airportRequest = mock(AirportRequestDto.class);
        var airport = mock(Airport.class);
        var createdAirport = mock(Airport.class);

        when(airportMapper.airportRequestDtoToAirport(airportRequest)).thenReturn(airport);
        when(airportService.create(airport)).thenReturn(createdAirport);

        airportController.create(airportRequest);

        verify(airportMapper).airportToAirportResponseDto(createdAirport);
    }

    @Test
    void update() {
    }

    @Test
    void get() {
    }

    @Test
    void delete() {
    }

    @Test
    void getAll() {
    }
}