package com.itkonboarding.airport_gate.controllers;

import com.itkonboarding.airport_gate.dto.request.FlightRequestDto;
import com.itkonboarding.airport_gate.dto.request.FlightUpdateRequestDto;
import com.itkonboarding.airport_gate.dto.response.FlightResponseDto;
import com.itkonboarding.airport_gate.entities.Flight;
import com.itkonboarding.airport_gate.exceptions.ResourceNotFoundException;
import com.itkonboarding.airport_gate.mappers.FlightMapper;
import com.itkonboarding.airport_gate.services.FlightService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Random;

import static com.itkonboarding.airport_gate.exceptions.ErrorCode.FLIGHT_NOT_FOUND;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightControllerTest {

    @Mock
    private FlightService flightService;

    @Mock
    private FlightMapper flightMapper;

    @InjectMocks
    private FlightController flightController;

    @Test
    void get_notFoundExtension() {
        var id = new Random().nextInt();
        var flight = mock(Flight.class);

        when(flightService.findById(id)).thenReturn(empty());

        assertAll(
                () -> assertThatExceptionOfType(ResourceNotFoundException.class)
                        .isThrownBy(() -> flightController.get(id))
                        .withMessage(FLIGHT_NOT_FOUND),
                () -> verify(flightMapper, never()).flightToFlightResponseDto(flight)
        );
    }

    @Test
    void get() {
        var id = new Random().nextInt();
        var flight = mock(Flight.class);
        var responseFlight = mock(FlightResponseDto.class);

        when(flightService.findById(id)).thenReturn(of(flight));
        when(flightMapper.flightToFlightResponseDto(flight)).thenReturn(responseFlight);

        var result = flightController.get(id);

        assertThat(result).isEqualTo(responseFlight);
    }

    @Test
    void create() {
        var requestFlight = mock(FlightRequestDto.class);
        var flight = mock(Flight.class);
        var createdFlight = mock(Flight.class);

        when(flightMapper.flightRequestDtoToFlight(requestFlight)).thenReturn(flight);
        when(flightService.create(flight)).thenReturn(createdFlight);

        flightController.create(requestFlight);

        verify(flightMapper).flightToFlightResponseDto(createdFlight);
    }

    @Test
    void update() {
        var flightId = new Random().nextInt();
        var flightRequest = mock(FlightUpdateRequestDto.class);
        var flight = mock(Flight.class);
        var updatedFlight = mock(Flight.class);

        when(flightMapper.flightUpdateRequestDtoToFlight(flightRequest)).thenReturn(flight);
        when(flightService.update(flightId, flightRequest.getGateId(), flight)).thenReturn(updatedFlight);

        flightController.update(flightId, flightRequest);

        verify(flightMapper).flightToFlightResponseDto(updatedFlight);
    }

    @Test
    void delete() {
        var id = new Random().nextInt();

        flightController.delete(id);

        verify(flightService).delete(id);
    }

    @Test
    void getAll() {
        var flights = List.of(mock(Flight.class));
        var response = List.of(mock(FlightResponseDto.class));

        when(flightService.getAll()).thenReturn(flights);
        when(flightMapper.flightToFlightResponseDto(flights.get(0))).thenReturn(response.get(0));

        var result = flightController.getAll();

        assertThat(result).isEqualTo(response);
    }
}
