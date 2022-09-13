package com.itkonboarding.airport_gate.services;

import com.itkonboarding.airport_gate.entities.Flight;
import com.itkonboarding.airport_gate.exceptions.ResourceNotFoundException;
import com.itkonboarding.airport_gate.repositories.FlightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.itkonboarding.airport_gate.entities.Gate.Status.UNAVAILABLE;
import static com.itkonboarding.airport_gate.exceptions.ErrorCode.*;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Implementation of {@link FlightService}
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    private final GateService gateService;

    @Override
    public Optional<Flight> findById(Integer id) {
        log.debug("Find flight with id {}", id);

        return flightRepository.findById(id);
    }

    @Override
    public Flight create(Flight flight) {
        log.debug("Create new flight with params {}", flight);

        return flightRepository.save(flight);
    }

    @Override
    @Transactional
    public Flight update(Integer flightId, Integer gateId, Flight flight) {
        log.debug("Update flight id {} with params {} and gateId {}", flightId, flight, gateId);

        var updatedFlight = flightRepository.findById(flightId).orElseThrow(() ->
                new ResourceNotFoundException(FLIGHT_NOT_FOUND));

        if (isNotBlank(flight.getFlightIndex())) {
            updatedFlight.setFlightIndex(flight.getFlightIndex());
        }

        if (nonNull(gateId)) {
            var gate = gateService.findById(gateId)
                    .orElseThrow(() -> new ResourceNotFoundException(GATE_NOT_FOUND));

            if (gate.getStatus().equals(UNAVAILABLE)) {
                throw new ResourceNotFoundException(GATE_NOT_AVAILABLE);
            }

            gateService.setUnavailable(gate);
            updatedFlight.setGate(gate);
        }

        return updatedFlight;
    }

    @Override
    public void delete(Integer id) {
        log.debug("Delete flight with id {}", id);

        var flight = flightRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(FLIGHT_NOT_FOUND));
        flightRepository.delete(flight);
    }

    @Override
    public List<Flight> getAll() {
        log.debug("Find all flights");

        return flightRepository.findAll();
    }
}
