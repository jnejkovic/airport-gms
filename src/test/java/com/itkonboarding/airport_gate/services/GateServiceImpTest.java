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

import java.util.Random;

import static com.itkonboarding.airport_gate.entities.Gate.Status.AVAILABLE;
import static com.itkonboarding.airport_gate.entities.Gate.Status.UNAVAILABLE;
import static com.itkonboarding.airport_gate.exceptions.ErrorCode.AIRPORT_NOT_FOUND;
import static com.itkonboarding.airport_gate.exceptions.ErrorCode.GATE_NOT_FOUND;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static net.bytebuddy.utility.RandomString.make;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GateServiceImpTest {

    @Mock
    private GateRepository gateRepository;

    @InjectMocks
    private GateServiceImp gateServiceImp;

    @Mock
    private AirportServiceImp airportServiceImp;

    @Test
    void findById() {
        var id = new Random().nextInt();

        gateServiceImp.findById(id);

        verify(gateRepository).findById(id);
    }

    @Test
    void create_notFoundException() {
        var id = new Random().nextInt();
        var gate = mock(Gate.class);

        when(airportServiceImp.findById(id)).thenReturn(empty());

        assertAll(
                () -> assertThatExceptionOfType(ResourceNotFoundException.class)
                        .isThrownBy(() -> gateServiceImp.create(id, gate))
                        .withMessage(AIRPORT_NOT_FOUND),
                () -> verifyNoInteractions(gate),
                () -> verifyNoInteractions(gateRepository)
        );
    }

    @Test
    void create_withoutAirportId() {
        Integer id = null;
        var gate = mock(Gate.class);

        gateServiceImp.create(id, gate);

        assertAll(
                () -> verify(gate).setStatus(AVAILABLE),
                () -> verify(gateRepository).save(gate),
                () -> verifyNoInteractions(airportServiceImp)
        );
    }

    @Test
    void create() {
        var id = new Random().nextInt();
        var gate = mock(Gate.class);
        var airport = mock(Airport.class);

        when(airportServiceImp.findById(id)).thenReturn(of(airport));

        gateServiceImp.create(id, gate);

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
                        .isThrownBy(() -> gateServiceImp.update(gateId, airportId, gate))
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

        when(airportServiceImp.findById(airportId)).thenReturn(empty());
        when(gateRepository.findById(gateId)).thenReturn(of(gate));

        assertAll(
                () -> assertThatExceptionOfType(ResourceNotFoundException.class)
                        .isThrownBy(() -> gateServiceImp.update(gateId, airportId, gate))
                        .withMessage(AIRPORT_NOT_FOUND),
                () -> verifyNoMoreInteractions(gateRepository)
        );
    }

    @Test
    void update() {
        var gateId = new Random().nextInt();
        var airportId = new Random().nextInt();
        var gate = mock(Gate.class);
        var updatedGate = mock(Gate.class);
        var gateName = make();
        var airport = mock(Airport.class);

        when(gate.getGateName()).thenReturn(gateName);
        when(airportServiceImp.findById(airportId)).thenReturn(of(airport));
        when(gateRepository.findById(gateId)).thenReturn(of(updatedGate));

        gateServiceImp.update(gateId, airportId, gate);

        assertAll(
                () -> verify(updatedGate).setGateName(gateName),
                () -> verify(updatedGate).setAirport(airport),
                () -> verify(gateRepository).save(updatedGate)
        );
    }

    @Test
    void update_withNull() {
        var gateId = new Random().nextInt();
        Integer airportId = null;
        var gate = mock(Gate.class);
        var updatedGate = mock(Gate.class);

        when(gateRepository.findById(gateId)).thenReturn(of(updatedGate));

        gateServiceImp.update(gateId, airportId, gate);

        assertAll(
                () -> verifyNoInteractions(updatedGate),
                () -> verify(gateRepository).save(updatedGate)
        );
    }

    @Test
    void delete_notFoundException() {
        var id = new Random().nextInt();

        when(gateRepository.findById(id)).thenReturn(empty());

        assertAll(
                () -> assertThatExceptionOfType(ResourceNotFoundException.class).isThrownBy(() ->
                        gateServiceImp.delete(id)).withMessage(GATE_NOT_FOUND),
                () -> verifyNoMoreInteractions(gateRepository)
        );
    }

    @Test
    void delete() {
        var id = new Random().nextInt();
        var gate = mock(Gate.class);

        when(gateRepository.findById(id)).thenReturn(of(gate));

        gateServiceImp.delete(id);

        verify(gateRepository).delete(gate);
    }

    @Test
    void getAllGatesForAirport_notFoundException() {
        var id = new Random().nextInt();
        var airport = mock(Airport.class);

        when(airportServiceImp.findById(id)).thenReturn(empty());

        assertAll(
                () -> assertThatExceptionOfType(ResourceNotFoundException.class)
                        .isThrownBy(() -> gateServiceImp.getAllGatesForAirport(id))
                        .withMessage(AIRPORT_NOT_FOUND),
                () -> verify(airport, never()).getGates()
        );
    }

    @Test
    void getAllGatesForAirport() {
        var id = new Random().nextInt();
        var airport = mock(Airport.class);

        when(airportServiceImp.findById(id)).thenReturn(of(airport));

        gateServiceImp.getAllGatesForAirport(id);

        verify(airport).getGates();
    }

    @Test
    void makeAvailable_notFoundException() {
        var id = new Random().nextInt();
        var gate = mock(Gate.class);

        when(gateRepository.findById(id)).thenReturn(empty());

        assertAll(
                () -> assertThatExceptionOfType(ResourceNotFoundException.class)
                        .isThrownBy(() -> gateServiceImp.makeAvailable(id))
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

        gateServiceImp.makeAvailable(id);

        assertAll(
                () -> verify(gate).setStatus(AVAILABLE),
                () -> verify(gateRepository).save(gate)
        );
    }

    @Test
    void setUnavailable() {
        var gate = mock(Gate.class);

        gateServiceImp.setUnavailable(gate);

        assertAll(
                () -> verify(gate).setStatus(UNAVAILABLE),
                () -> verify(gateRepository).save(gate)
        );
    }
}