package com.itkonboarding.airport_gate.services;

import com.itkonboarding.airport_gate.entities.Gate;
import com.itkonboarding.airport_gate.exceptions.ResourceNotFoundException;
import com.itkonboarding.airport_gate.repositories.GateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.itkonboarding.airport_gate.entities.Gate.Status.AVAILABLE;
import static com.itkonboarding.airport_gate.entities.Gate.Status.UNAVAILABLE;
import static com.itkonboarding.airport_gate.exceptions.ErrorCode.AIRPORT_NOT_FOUND;
import static com.itkonboarding.airport_gate.exceptions.ErrorCode.GATE_CURRENT_NOT_AVAILABLE;
import static com.itkonboarding.airport_gate.exceptions.ErrorCode.GATE_NOT_FOUND;
import static java.time.LocalTime.now;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Implementation of {@link GateService}
 *
 * @author jnejkovic
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GateServiceImpl implements GateService {

    private final GateRepository gateRepository;

    private final AirportService airportService;

    @Override
    public Optional<Gate> findById(Integer id) {
        log.debug("Find gate with id {}", id);

        return gateRepository.findById(id);
    }

    @Override
    @Transactional
    public Gate create(Integer airportId, Gate gate) {
        log.debug("Create new gate with params {} and airportId {}", gate, airportId);

        if (nonNull(airportId)) {
            var airport = airportService.findById(airportId)
                    .orElseThrow(() -> new ResourceNotFoundException(AIRPORT_NOT_FOUND));
            gate.setAirport(airport);
        }

        gate.setStatus(AVAILABLE);
        return gateRepository.save(gate);
    }

    @Override
    @Transactional
    public Gate update(Integer gateId, Integer airportId, Gate gate) {
        log.debug("Update gate id {} with params {} and airportId {}", gateId, gate, airportId);

        var updatedGate = gateRepository.findById(gateId).orElseThrow(() ->
                new ResourceNotFoundException(GATE_NOT_FOUND));

        if (isNotBlank(gate.getGateName())) {
            updatedGate.setGateName(gate.getGateName());
        }

        if (nonNull(gate.getAvailableFrom())) {
            updatedGate.setAvailableFrom(gate.getAvailableFrom());
        }
        if (nonNull(gate.getAvailableTo())) {
            updatedGate.setAvailableTo(gate.getAvailableTo());
        }

        if (nonNull(airportId)) {
            var airport = airportService.findById(airportId)
                    .orElseThrow(() -> new ResourceNotFoundException(AIRPORT_NOT_FOUND));
            updatedGate.setAirport(airport);
        }

        return updatedGate;
    }

    @Override
    public void delete(Integer id) {
        log.debug("Delete gate with id {}", id);

        var gate = gateRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(GATE_NOT_FOUND));
        gateRepository.delete(gate);
    }

    @Override
    public List<Gate> getAllGatesForAirport(Integer id) {
        log.debug("Get all gates for airport id {}", id);

        var airport = airportService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AIRPORT_NOT_FOUND));

        return airport.getGates();
    }

    @Override
    public Gate makeAvailable(Integer id) {
        log.debug("Change gate id {} to available", id);

        var gate = gateRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(GATE_NOT_FOUND));
        gate.setStatus(AVAILABLE);

        return gateRepository.save(gate);
    }

    @Override
    public void setUnavailable(Gate gate) {
        log.debug("Set status to unavailable for gate {}", gate);

        gate.setStatus(UNAVAILABLE);
        gateRepository.save(gate);
    }

    public boolean isGateAvailable(Gate gate) {
        var currentTime = now();
        if (currentTime.isAfter(gate.getAvailableTo()) || currentTime.isBefore(gate.getAvailableFrom())) {
            throw new ResourceNotFoundException(GATE_CURRENT_NOT_AVAILABLE);
        }
        return true;
    }
}
