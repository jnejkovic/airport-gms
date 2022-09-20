package com.itkonboarding.airport_gate.services;

import com.itkonboarding.airport_gate.entities.Airport;
import com.itkonboarding.airport_gate.entities.Gate;
import com.itkonboarding.airport_gate.exceptions.ResourceNotFoundException;
import com.itkonboarding.airport_gate.repositories.GateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Random;

import static com.itkonboarding.airport_gate.entities.Gate.Status.AVAILABLE;
import static com.itkonboarding.airport_gate.entities.Gate.Status.UNAVAILABLE;
import static com.itkonboarding.airport_gate.exceptions.ErrorCode.AIRPORT_NOT_FOUND;
import static com.itkonboarding.airport_gate.exceptions.ErrorCode.GATE_CURRENT_NOT_AVAILABLE;
import static com.itkonboarding.airport_gate.exceptions.ErrorCode.GATE_NOT_FOUND;
import static java.time.LocalTime.now;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static net.bytebuddy.utility.RandomString.make;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GateServiceImplTest {

    @Mock
    private GateRepository gateRepository;

    @Mock
    private AirportService airportService;

    @InjectMocks
    private GateServiceImpl gateServiceImpl;

    @Test
    void findById() {
        var id = new Random().nextInt();

        gateServiceImpl.findById(id);

        verify(gateRepository).findById(id);
    }

    @Test
    void create_notFoundException() {
        var id = new Random().nextInt();
        var gate = mock(Gate.class);

        when(airportService.findById(id)).thenReturn(empty());

        assertAll(
                () -> assertThatExceptionOfType(ResourceNotFoundException.class)
                        .isThrownBy(() -> gateServiceImpl.create(id, gate))
                        .withMessage(AIRPORT_NOT_FOUND),
                () -> verifyNoInteractions(gate),
                () -> verifyNoInteractions(gateRepository)
        );
    }

    @Test
    void create_withoutAirportId() {
        Integer id = null;
        var gate = mock(Gate.class);

        gateServiceImpl.create(id, gate);

        assertAll(
                () -> verify(gate).setStatus(AVAILABLE),
                () -> verify(gateRepository).save(gate),
                () -> verifyNoInteractions(airportService)
        );
    }

    @Test
    void create() {
        var id = new Random().nextInt();
        var gate = mock(Gate.class);
        var airport = mock(Airport.class);

        when(airportService.findById(id)).thenReturn(of(airport));

        gateServiceImpl.create(id, gate);

        assertAll(
                () -> verify(gate).setAirport(airport),
                () -> verify(gate).setStatus(AVAILABLE),
                () -> verify(gateRepository).save(gate)
        );
    }

    @Test
    void update_noGateFoundException() {
        var gateId = new Random().nextInt();
        var airportId = new Random().nextInt();
        var gate = mock(Gate.class);

        when(gateRepository.findById(gateId)).thenReturn(empty());

        assertAll(
                () -> assertThatExceptionOfType(ResourceNotFoundException.class)
                        .isThrownBy(() -> gateServiceImpl.update(gateId, airportId, gate))
                        .withMessage(GATE_NOT_FOUND),
                () -> verifyNoInteractions(gate),
                () -> verifyNoMoreInteractions(gateRepository)
        );
    }

    @Test
    void update_noAirportFoundException() {
        var gateId = new Random().nextInt();
        var airportId = new Random().nextInt();
        var gate = mock(Gate.class);

        when(airportService.findById(airportId)).thenReturn(empty());
        when(gateRepository.findById(gateId)).thenReturn(of(gate));

        assertAll(
                () -> assertThatExceptionOfType(ResourceNotFoundException.class)
                        .isThrownBy(() -> gateServiceImpl.update(gateId, airportId, gate))
                        .withMessage(AIRPORT_NOT_FOUND),
                () -> verifyNoMoreInteractions(gateRepository)
        );
    }

    @Test
    void update() {
        var gateId = Math.abs(new Random().nextInt());
        var airportId = new Random().nextInt();
        var gate = mock(Gate.class);
        var updatedGate = mock(Gate.class);
        var gateName = make();
        var airport = mock(Airport.class);
        var gateAvailableFrom = now().minusHours(3);
        var gateAvailableTo = now().plusHours(3);

        when(gate.getGateName()).thenReturn(gateName);
        when(gate.getAvailableFrom()).thenReturn(gateAvailableFrom);
        when(gate.getAvailableTo()).thenReturn(gateAvailableTo);
        when(airportService.findById(airportId)).thenReturn(of(airport));
        when(gateRepository.findById(gateId)).thenReturn(of(updatedGate));

        var result = gateServiceImpl.update(gateId, airportId, gate);

        assertAll(
                () -> verify(updatedGate).setGateName(gateName),
                () -> verify(updatedGate).setAvailableFrom(gateAvailableFrom),
                () -> verify(updatedGate).setAvailableTo(gateAvailableTo),
                () -> verify(updatedGate).setAirport(airport),
                () -> assertThat(result).isEqualTo(updatedGate)
        );
    }

    @Test
    void update_gateAvailableToIsNull() {
        var gateId = Math.abs(new Random().nextInt());
        var airportId = new Random().nextInt();
        var gate = mock(Gate.class);
        var updatedGate = mock(Gate.class);
        var gateName = make();
        var airport = mock(Airport.class);
        var availableFrom = now().minusHours(3);

        when(gate.getGateName()).thenReturn(gateName);
        when(gate.getAvailableFrom()).thenReturn(availableFrom);
        when(gate.getAvailableTo()).thenReturn(null);
        when(airportService.findById(airportId)).thenReturn(of(airport));
        when(gateRepository.findById(gateId)).thenReturn(of(updatedGate));

        var result = gateServiceImpl.update(gateId, airportId, gate);

        assertAll(
                () -> verify(updatedGate).setGateName(gateName),
                () -> verify(updatedGate).setAirport(airport),
                () -> assertThat(result).isEqualTo(updatedGate)
        );
    }

    @Test
    void update_withNull() {
        var gateId = new Random().nextInt();
        Integer airportId = null;
        var gate = mock(Gate.class);
        var updatedGate = mock(Gate.class);

        when(gateRepository.findById(gateId)).thenReturn(of(updatedGate));

        var result = gateServiceImpl.update(gateId, airportId, gate);

        assertAll(
                () -> verifyNoInteractions(updatedGate),
                () -> assertThat(result).isEqualTo(updatedGate)
        );
    }

    @Test
    void delete_notFoundException() {
        var id = new Random().nextInt();

        when(gateRepository.findById(id)).thenReturn(empty());

        assertAll(
                () -> assertThatExceptionOfType(ResourceNotFoundException.class).isThrownBy(() ->
                        gateServiceImpl.delete(id)).withMessage(GATE_NOT_FOUND),
                () -> verifyNoMoreInteractions(gateRepository)
        );
    }

    @Test
    void delete() {
        var id = new Random().nextInt();
        var gate = mock(Gate.class);

        when(gateRepository.findById(id)).thenReturn(of(gate));

        gateServiceImpl.delete(id);

        verify(gateRepository).delete(gate);
    }

    @Test
    void getAllGatesForAirport_notFoundException() {
        var id = new Random().nextInt();
        var airport = mock(Airport.class);

        when(airportService.findById(id)).thenReturn(empty());

        assertAll(
                () -> assertThatExceptionOfType(ResourceNotFoundException.class)
                        .isThrownBy(() -> gateServiceImpl.getAllGatesForAirport(id))
                        .withMessage(AIRPORT_NOT_FOUND),
                () -> verify(airport, never()).getGates()
        );
    }

    @Test
    void getAllGatesForAirport() {
        var id = new Random().nextInt();
        var airport = mock(Airport.class);

        when(airportService.findById(id)).thenReturn(of(airport));

        gateServiceImpl.getAllGatesForAirport(id);

        verify(airport).getGates();
    }

    @Test
    void makeAvailable_notFoundException() {
        var id = new Random().nextInt();
        var gate = mock(Gate.class);

        when(gateRepository.findById(id)).thenReturn(empty());

        assertAll(
                () -> assertThatExceptionOfType(ResourceNotFoundException.class)
                        .isThrownBy(() -> gateServiceImpl.makeAvailable(id))
                        .withMessage(GATE_NOT_FOUND),
                () -> verify(gate, never()).setStatus(AVAILABLE),
                () -> verifyNoMoreInteractions(gateRepository)
        );
    }

    @Test
    void makeAvailable() {
        var id = new Random().nextInt();
        var gate = mock(Gate.class);

        when(gateRepository.findById(id)).thenReturn(of(gate));

        gateServiceImpl.makeAvailable(id);

        assertAll(
                () -> verify(gate).setStatus(AVAILABLE),
                () -> verify(gateRepository).save(gate)
        );
    }

    @Test
    void setUnavailable() {
        var gate = mock(Gate.class);

        gateServiceImpl.setUnavailable(gate);

        assertAll(
                () -> verify(gate).setStatus(UNAVAILABLE),
                () -> verify(gateRepository).save(gate)
        );
    }

    @Test
    void isGateAvailable() {
        var gate = mock(Gate.class);
        var availableFrom = now().minusHours(3);
        var availableTo = now().plusHours(3);

        when(gate.getAvailableTo()).thenReturn(availableTo);
        when(gate.getAvailableFrom()).thenReturn(availableFrom);

        var result = gateServiceImpl.isGateAvailable(gate);

        assertThat(result).isEqualTo(true);
    }

    @Test
    void isGateAvailable_currentTimeGreaterThanAvailableTo() {
        var gate = mock(Gate.class);
        var availableTo = now().minusHours(3);
        var availableFrom = now().minusHours(6);

        when(gate.getAvailableTo()).thenReturn(availableTo);
        when(gate.getAvailableFrom()).thenReturn(availableFrom);

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> gateServiceImpl.isGateAvailable(gate))
                .withMessage(GATE_CURRENT_NOT_AVAILABLE);
    }

    @Test
    void isGateAvailable_currentTimeLessThanAvailableFrom() {
        var gate = mock(Gate.class);
        var availableTo = now().plusHours(6);
        var availableFrom = now().plusHours(3);

        when(gate.getAvailableFrom()).thenReturn(availableFrom);
        when(gate.getAvailableTo()).thenReturn(availableTo);

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> gateServiceImpl.isGateAvailable(gate))
                .withMessage(GATE_CURRENT_NOT_AVAILABLE);
    }

    @Test
    void isGateAvailable_availableFromAndAvailableToAreNull() {
        var gate = mock(Gate.class);

        var result = gateServiceImpl.isGateAvailable(gate);

        assertThat(result).isEqualTo(false);
    }

    @Test
    void isGateAvailable_availableFromIsNull() {
        var gate = mock(Gate.class);
        var availableFrom = now().minusHours(3);

        when(gate.getAvailableFrom()).thenReturn(availableFrom);

        var result = gateServiceImpl.isGateAvailable(gate);

        assertThat(result).isEqualTo(false);
    }
}
