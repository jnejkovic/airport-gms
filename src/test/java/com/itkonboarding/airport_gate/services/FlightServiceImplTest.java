package com.itkonboarding.airport_gate.services;

import com.itkonboarding.airport_gate.entities.Flight;
import com.itkonboarding.airport_gate.entities.Gate;
import com.itkonboarding.airport_gate.exceptions.ResourceNotFoundException;
import com.itkonboarding.airport_gate.repositories.FlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static com.itkonboarding.airport_gate.entities.Gate.Status.AVAILABLE;
import static com.itkonboarding.airport_gate.entities.Gate.Status.UNAVAILABLE;
import static com.itkonboarding.airport_gate.exceptions.ErrorCode.*;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightServiceImplTest {

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private GateService gateService;

    @InjectMocks
    private FlightServiceImpl flightServiceImpl;

    @Test
    void findById() {
        var id = new Random().nextInt();

        flightServiceImpl.findById(id);

        verify(flightRepository).findById(id);
    }

    @Test
    void create() {
        var flight = mock(Flight.class);

        flightServiceImpl.create(flight);

        verify(flightRepository).save(flight);
    }

    @Test
    void update_flightNotFoundException() {
        var flightId = new Random().nextInt();
        var gateId = new Random().nextInt();
        var flight = mock(Flight.class);
        var updatedFlight = mock(Flight.class);
        var gate = mock(Gate.class);

        when(flightRepository.findById(flightId)).thenReturn(empty());

        assertAll(
                () -> assertThatExceptionOfType(ResourceNotFoundException.class)
                        .isThrownBy(() -> flightServiceImpl.update(flightId, gateId, flight))
                        .withMessage(FLIGHT_NOT_FOUND),
                () -> verify(updatedFlight, never()).setFlightIndex(flight.getFlightIndex()),
                () -> verify(gateService, never()).setUnavailable(gate),
                () -> verify(updatedFlight, never()).setGate(gate),
                () -> verifyNoMoreInteractions(flightRepository)
        );
    }

    @Test
    void update_gateNotFoundException() {
        var flightId = new Random().nextInt();
        var gateId = new Random().nextInt();
        var flight = mock(Flight.class);
        var updatedFlight = mock(Flight.class);
        var gate = mock(Gate.class);

        when(flightRepository.findById(flightId)).thenReturn(of(updatedFlight));
        when(gateService.findById(gateId)).thenReturn(empty());

        assertAll(
                () -> assertThatExceptionOfType(ResourceNotFoundException.class)
                        .isThrownBy(() -> flightServiceImpl.update(flightId, gateId, flight))
                        .withMessage(GATE_NOT_FOUND),
                () -> verify(updatedFlight, never()).setFlightIndex(flight.getFlightIndex()),
                () -> verify(gateService, never()).setUnavailable(gate),
                () -> verify(updatedFlight, never()).setGate(gate),
                () -> verifyNoMoreInteractions(flightRepository)
        );
    }

    @Test
    void update_gateNotAvailableException() {
        var flightId = new Random().nextInt();
        var gateId = new Random().nextInt();
        var flight = mock(Flight.class);
        var gate = mock(Gate.class);
        var updatedFlight = mock(Flight.class);

        when(gateService.findById(gateId)).thenReturn(of(gate));
        when(gate.getStatus()).thenReturn(UNAVAILABLE);
        when(flightRepository.findById(flightId)).thenReturn(of(updatedFlight));

        assertAll(
                () -> assertThatExceptionOfType(ResourceNotFoundException.class)
                        .isThrownBy(() -> flightServiceImpl.update(flightId, gateId, flight))
                        .withMessage(GATE_NOT_AVAILABLE),
                () -> verify(updatedFlight, never()).setFlightIndex(flight.getFlightIndex()),
                () -> verify(gateService, never()).setUnavailable(gate),
                () -> verify(updatedFlight, never()).setGate(gate),
                () -> verifyNoMoreInteractions(flightRepository)
        );
    }

    @Test
    void update() {
        var flightId = new Random().nextInt();
        var gateId = new Random().nextInt();
        var flight = mock(Flight.class);
        var flightIndex = randomAlphanumeric(4);
        var updatedFlight = mock(Flight.class);
        var gate = mock(Gate.class);

        when(flightRepository.findById(flightId)).thenReturn(of(updatedFlight));
        when(flight.getFlightIndex()).thenReturn(flightIndex);
        when(gateService.findById(gateId)).thenReturn(of(gate));
        when(gate.getStatus()).thenReturn(AVAILABLE);

        var result = flightServiceImpl.update(flightId, gateId, flight);

        assertAll(
                () -> verify(updatedFlight).setFlightIndex(flightIndex),
                () -> verify(gateService).setUnavailable(gate),
                () -> verify(updatedFlight).setGate(gate),
                () -> assertThat(result).isEqualTo(updatedFlight)
        );
    }

    @Test
    void update_gateIdNull() {
        var flightId = new Random().nextInt();
        Integer gateId = null;
        var flight = mock(Flight.class);
        var flightIndex = randomAlphanumeric(4);
        var updatedFlight = mock(Flight.class);

        when(flightRepository.findById(flightId)).thenReturn(of(updatedFlight));
        when(flight.getFlightIndex()).thenReturn(flightIndex);

        var result = flightServiceImpl.update(flightId, gateId, flight);

        assertAll(
                () -> verify(updatedFlight).setFlightIndex(flightIndex),
                () -> assertThat(result).isEqualTo(updatedFlight)
        );
    }

    @Test
    void delete_notFoundException() {
        var id = new Random().nextInt();

        when(flightRepository.findById(id)).thenReturn(empty());

        assertAll(
                () -> assertThatExceptionOfType(ResourceNotFoundException.class).isThrownBy(() ->
                        flightServiceImpl.delete(id)).withMessage(FLIGHT_NOT_FOUND),
                () -> verifyNoMoreInteractions(flightRepository)
        );
    }

    @Test
    void delete() {
        var id = new Random().nextInt();
        var flight = mock(Flight.class);

        when(flightRepository.findById(id)).thenReturn(of(flight));

        flightServiceImpl.delete(id);

        verify(flightRepository).delete(flight);
    }

    @Test
    void getAll() {
        flightServiceImpl.getAll();

        verify(flightRepository).findAll();
    }
}
