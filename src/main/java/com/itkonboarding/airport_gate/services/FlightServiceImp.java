package com.itkonboarding.airport_gate.services;

import com.itkonboarding.airport_gate.entities.Flight;
import com.itkonboarding.airport_gate.exceptions.ResourceNotFoundException;
import com.itkonboarding.airport_gate.repositories.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
public class FlightServiceImp implements FlightService {

    private final FlightRepository flightRepository;

    private final GateService gateService;

    @Override
    public Optional<Flight> findById(Integer id) {
        return flightRepository.findById(id);
    }

    @Override
    public Flight create(Flight flight) {
        return flightRepository.save(flight);
    }

    @Override
    public Flight update(Integer flightId, Integer gateId, Flight flight) {
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

        return flightRepository.save(updatedFlight);
    }

    @Override
    public void delete(Integer id) {
        var flight = flightRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(FLIGHT_NOT_FOUND));
        flightRepository.delete(flight);
    }

    @Override
    public List<Flight> getAll() {
        return flightRepository.findAll();
    }
}
