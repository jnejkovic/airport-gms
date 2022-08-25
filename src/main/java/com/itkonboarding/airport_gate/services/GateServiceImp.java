package com.itkonboarding.airport_gate.services;

import com.itkonboarding.airport_gate.entities.Gate;
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

    @Override
    public Optional<Gate> findById(Integer id) {
        return gateRepository.findById(id);
    }

    @Override
    public Gate create(Gate gate) {
        var airport = airportRepository.findById(gate.getAirport().getId()).orElseThrow(() -> new RuntimeException("Airport not found"));
        gateRepository.save(gate);
        return gate;
    }

    @Override
    public Gate update(Integer id, Gate gate) {
        var updatedGate = gateRepository.findById(id).orElseThrow(() -> new RuntimeException("Gate not found"));

        if (isNotBlank(gate.getGateName())) {
            updatedGate.setGateName(gate.getGateName());
        }

        if (nonNull(gate.getAirport())) {
            var airport = airportRepository.findById(gate.getAirport().getId()).orElseThrow(() -> new RuntimeException("Airport not found"));
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
