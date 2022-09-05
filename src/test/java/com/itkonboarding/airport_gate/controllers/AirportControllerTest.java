package com.itkonboarding.airport_gate.controllers;

import com.itkonboarding.airport_gate.dto.request.AirportRequestDto;
import com.itkonboarding.airport_gate.dto.response.AirportResponseDto;
import com.itkonboarding.airport_gate.entities.Airport;
import com.itkonboarding.airport_gate.exceptions.ResourceNotFoundException;
import com.itkonboarding.airport_gate.mappers.AirportMapper;
import com.itkonboarding.airport_gate.services.AirportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Random;

import static com.itkonboarding.airport_gate.exceptions.ErrorCode.AIRPORT_NOT_FOUND;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
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
        var id = new Random().nextInt();
        var airportRequest = mock(AirportRequestDto.class);
        var updatedAirport = mock(Airport.class);

        when(airportService.update(id, airportMapper.airportRequestDtoToAirport(airportRequest)))
                .thenReturn(updatedAirport);

        airportController.update(id, airportRequest);

        verify(airportMapper).airportToAirportResponseDto(updatedAirport);
    }

    @Test
    void get_notFoundException() {
        var id = new Random().nextInt();
        var airport = mock(Airport.class);

        when(airportService.findById(id)).thenReturn(empty());

        assertAll(
                () -> assertThatExceptionOfType(ResourceNotFoundException.class)
                        .isThrownBy(() -> airportController.get(id))
                        .withMessage(AIRPORT_NOT_FOUND),
                () -> verify(airportMapper, never()).airportToAirportResponseDto(airport)
        );
    }

    @Test
    void get() {
        var id = new Random().nextInt();
        var airport = mock(Airport.class);
        var responseAirport = mock(AirportResponseDto.class);

        when(airportService.findById(id)).thenReturn(of(airport));
        when(airportMapper.airportToAirportResponseDto(airport)).thenReturn(responseAirport);

        var result = airportController.get(id);

        assertThat(result).isEqualTo(responseAirport);
    }

    @Test
    void delete() {
        var id = new Random().nextInt();

        airportController.delete(id);

        verify(airportService).delete(id);
    }

    @Test
    void getAll() {
        var airports = List.of(mock(Airport.class));
        var response = List.of(mock(AirportResponseDto.class));

        when(airportService.getAll()).thenReturn(airports);
        when(airportMapper.airportToAirportResponseDto(airports.get(0))).thenReturn(response.get(0));

        var result = airportController.getAll();

        assertThat(result).isEqualTo(response);
    }
}
