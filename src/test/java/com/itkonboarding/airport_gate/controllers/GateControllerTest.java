package com.itkonboarding.airport_gate.controllers;

import com.itkonboarding.airport_gate.dto.request.GateRequestDto;
import com.itkonboarding.airport_gate.dto.request.GateUpdateRequestDto;
import com.itkonboarding.airport_gate.dto.response.GateResponseDto;
import com.itkonboarding.airport_gate.entities.Gate;
import com.itkonboarding.airport_gate.exceptions.ResourceNotFoundException;
import com.itkonboarding.airport_gate.mappers.GateMapper;
import com.itkonboarding.airport_gate.services.GateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Random;

import static com.itkonboarding.airport_gate.exceptions.ErrorCode.GATE_NOT_FOUND;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GateControllerTest {

    @Mock
    private GateService gateService;

    @Mock
    private GateMapper gateMapper;

    @InjectMocks
    private GateController gateController;

    @Test
    void get_notFoundException() {
        var id = new Random().nextInt();
        var gate = mock(Gate.class);

        when(gateService.findById(id)).thenReturn(empty());

        assertAll(
                () -> assertThatExceptionOfType(ResourceNotFoundException.class)
                        .isThrownBy(() -> gateController.get(id))
                        .withMessage(GATE_NOT_FOUND),
                () -> verify(gateMapper, never()).gateToGateResponseDto(gate)
        );
    }

    @Test
    void get() {
        var id = new Random().nextInt();
        var gate = mock(Gate.class);
        var responseGate = mock(GateResponseDto.class);

        when(gateService.findById(id)).thenReturn(of(gate));
        when(gateMapper.gateToGateResponseDto(gate)).thenReturn(responseGate);

        var result = gateController.get(id);

        assertThat(result).isEqualTo(responseGate);
    }

    @Test
    void create() {
        var requestGate = mock(GateRequestDto.class);
        var gate = mock(Gate.class);
        var createdGate = mock(Gate.class);

        when(gateMapper.gateRequestDtoToGate(requestGate)).thenReturn(gate);
        when(gateService.create(requestGate.getAirportId(), gate)).thenReturn(createdGate);

        gateController.create(requestGate);

        verify(gateMapper).gateToGateResponseDto(createdGate);
    }

    @Test
    void update() {
        var gateId = new Random().nextInt();
        var requestGate = mock(GateUpdateRequestDto.class);
        var gate = mock(Gate.class);
        var updatedGate = mock(Gate.class);

        when(gateMapper.gateUpdateRequestDtoToGate(requestGate)).thenReturn(gate);
        when(gateService.update(gateId, requestGate.getAirportId(), gate)).thenReturn(updatedGate);

        gateController.update(gateId, requestGate);

        verify(gateMapper).gateToGateResponseDto(updatedGate);
    }

    @Test
    void makeAvailable() {
        var id = new Random().nextInt();
        var gate = mock(Gate.class);
        var response = mock(GateResponseDto.class);

        when(gateService.makeAvailable(id)).thenReturn(gate);
        when(gateMapper.gateToGateResponseDto(gate)).thenReturn(response);

        var result = gateController.makeAvailable(id);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void delete() {
        var id = new Random().nextInt();

        gateController.delete(id);

        verify(gateService).delete(id);
    }

    @Test
    void getAllGatesForAirport() {
        var airportId = new Random().nextInt();
        var gates = List.of(mock(Gate.class));
        var response = List.of(mock(GateResponseDto.class));

        when(gateService.getAllGatesForAirport(airportId)).thenReturn(gates);
        when(gateMapper.gateToGateResponseDto(gates.get(0))).thenReturn(response.get(0));

        var result = gateController.getAllGatesForAirport(airportId);

        assertThat(result).isEqualTo(response);
    }
}
