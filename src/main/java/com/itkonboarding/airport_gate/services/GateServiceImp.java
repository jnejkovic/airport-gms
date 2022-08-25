package com.itkonboarding.airport_gate.services;

import com.itkonboarding.airport_gate.dto.request.GateRequestDto;
import com.itkonboarding.airport_gate.dto.request.GateUpdateRequestDto;
import com.itkonboarding.airport_gate.dto.response.GateResponseDto;
import com.itkonboarding.airport_gate.entities.Gate;
import com.itkonboarding.airport_gate.mappers.MapStructMapper;
import com.itkonboarding.airport_gate.repositories.AirportRepository;
import com.itkonboarding.airport_gate.repositories.GateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Implementation of {@link GateService}
 *
 * @author jnejkovic
 */
@Service
@RequiredArgsConstructor
public class GateServiceImp implements GateService {

    private final GateRepository gateRepository;

    private final AirportRepository airportRepository;

    private final MapStructMapper mapStructMapper;

    @Override
    public Optional<Gate> findById(Integer id) {
        return gateRepository.findById(id);
    }

    @Override
    public GateResponseDto create(GateRequestDto gate) {
        var newGate = new Gate();
        newGate.setGateName(gate.getGateName());
        var airport = airportRepository.findById(gate.getAirportId()).orElseThrow(() -> new RuntimeException("Airport not found"));
        newGate.setAirport(airport);
        gateRepository.save(newGate);
        return mapStructMapper.gateToGateResponseDto(newGate);
    }

    @Override
    public GateResponseDto update(Integer id, GateUpdateRequestDto gate) {
        var updatedGate = gateRepository.findById(id).orElseThrow(() -> new RuntimeException("Gate not found"));

        if (isNotBlank(gate.getGateName())) {
            updatedGate.setGateName(gate.getGateName());
        }

        if (nonNull(gate.getAirportId())) {
            var airport = airportRepository.findById(gate.getAirportId()).orElseThrow(() -> new RuntimeException("Airport not found"));
            updatedGate.setAirport(airport);
        }

        gateRepository.save(updatedGate);
        return mapStructMapper.gateToGateResponseDto(updatedGate);
    }

    @Override
    public void delete(Integer id) {
        var gate = gateRepository.findById(id).orElseThrow(() -> {
            return new RuntimeException("Gate not found");
        });
        gateRepository.delete(gate);
    }
}
