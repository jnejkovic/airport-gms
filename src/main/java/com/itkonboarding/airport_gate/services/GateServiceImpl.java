package com.itkonboarding.airport_gate.services;

import com.itkonboarding.airport_gate.entities.Gate;
import com.itkonboarding.airport_gate.exceptions.ResourceNotFoundException;
import com.itkonboarding.airport_gate.repositories.GateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.itkonboarding.airport_gate.entities.Gate.Status.AVAILABLE;
import static com.itkonboarding.airport_gate.entities.Gate.Status.UNAVAILABLE;
import static com.itkonboarding.airport_gate.exceptions.ErrorCode.AIRPORT_NOT_FOUND;
import static com.itkonboarding.airport_gate.exceptions.ErrorCode.GATE_NOT_FOUND;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Implementation of {@link GateService}
 *
 * @author jnejkovic
 */
@Service
@RequiredArgsConstructor
public class GateServiceImpl implements GateService {

    private final GateRepository gateRepository;

    private final AirportService airportService;

    @Override
    public Optional<Gate> findById(Integer id) {
        return gateRepository.findById(id);
    }

    @Override
    public Gate create(Integer airportId, Gate gate) {

        if (nonNull(airportId)) {
            var airport = airportService.findById(airportId)
                    .orElseThrow(() -> new ResourceNotFoundException(AIRPORT_NOT_FOUND));
            gate.setAirport(airport);
        }

        gate.setStatus(AVAILABLE);
        return gateRepository.save(gate);
    }

    @Override
    public Gate update(Integer gateId, Integer airportId, Gate gate) {
        var updatedGate = gateRepository.findById(gateId).orElseThrow(() ->
                new ResourceNotFoundException(GATE_NOT_FOUND));

        if (isNotBlank(gate.getGateName())) {
            updatedGate.setGateName(gate.getGateName());
        }

        if (nonNull(airportId)) {
            var airport = airportService.findById(airportId)
                    .orElseThrow(() -> new ResourceNotFoundException(AIRPORT_NOT_FOUND));
            updatedGate.setAirport(airport);
        }

        return gateRepository.save(updatedGate);
    }

    @Override
    public void delete(Integer id) {
        var gate = gateRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(GATE_NOT_FOUND));
        gateRepository.delete(gate);
    }

    @Override
    public List<Gate> getAllGatesForAirport(Integer id) {
        var airport = airportService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AIRPORT_NOT_FOUND));

        return airport.getGates();
    }

    @Override
    public Gate makeAvailable(Integer id) {
        var gate = gateRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(GATE_NOT_FOUND));
        gate.setStatus(AVAILABLE);

        return gateRepository.save(gate);
    }

    @Override
    public void setUnavailable(Gate gate) {
        gate.setStatus(UNAVAILABLE);
        gateRepository.save(gate);
    }
}
