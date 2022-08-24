package com.itkonboarding.airport_gate.services;

import com.itkonboarding.airport_gate.dto.request.GateRequestDto;
import com.itkonboarding.airport_gate.dto.request.GateUpdateRequestDto;
import com.itkonboarding.airport_gate.entities.Gate;
import com.itkonboarding.airport_gate.repositories.AirportRepository;
import com.itkonboarding.airport_gate.repositories.GateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

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

    @Override
    public Optional<Gate> getById(Integer id) {
        return gateRepository.findById(id);
    }

    @Override
    public Gate create(GateRequestDto gate) {
        var newGate = new Gate();
        newGate.setGateName(gate.getGateName());
        var airport = airportRepository.findById(gate.getAirportId()).orElseThrow(() -> {
            return new RuntimeException("Airport not found");
        });
        newGate.setAirport(airport);
        gateRepository.save(newGate);
        return newGate;
    }

    @Override
    public Gate update(Integer id, GateUpdateRequestDto gate) {
        var updatedGate = gateRepository.findById(id).orElseThrow(() -> {
            return new RuntimeException("Gate not found");
        });
        if (!gate.getGateName().isEmpty()) {
            updatedGate.setGateName(gate.getGateName());
        }
        if (Objects.nonNull(gate.getAirportId())) {
            var airport = airportRepository.findById(gate.getAirportId()).orElseThrow(() -> {
                return new RuntimeException("Airport not found");
            });
            updatedGate.setAirport(airport);
        }
        gateRepository.save(updatedGate);
        return updatedGate;
    }

    @Override
    public void delete(Integer id) {
        var gate = gateRepository.findById(id).orElseThrow(() -> {
            return new RuntimeException("Gate not found");
        });
        gateRepository.delete(gate);
    }
}
