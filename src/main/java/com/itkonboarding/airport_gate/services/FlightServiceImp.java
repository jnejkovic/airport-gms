package com.itkonboarding.airport_gate.services;

import com.itkonboarding.airport_gate.entities.Flight;
import com.itkonboarding.airport_gate.repositories.FlightRepository;
import com.itkonboarding.airport_gate.repositories.GateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Implementation of {@link FlightService}
 */
@Service
@RequiredArgsConstructor
public class FlightServiceImp implements FlightService {

    private final FlightRepository flightRepository;

    private final GateRepository gateRepository;

    @Override
    public Optional<Flight> findById(Integer id) {
        return flightRepository.findById(id);
    }

    //TODO check if gate is available
    @Override
    public Flight create(Flight flight) {
        var gate = gateRepository.findById(flight.getGate().getId()).orElseThrow(() ->
                new RuntimeException("Gate not found"));
        flightRepository.save(flight);
        return flight;
    }

    //TODO check if gate is available
    @Override
    public Flight update(Integer id, Flight flight) {
        var updatedFlight = flightRepository.findById(id).orElseThrow(() -> new RuntimeException("Flight not found"));

        if (isNotBlank(flight.getFlightIndex())) {
            updatedFlight.setFlightIndex(flight.getFlightIndex());
        }

        if (nonNull(flight.getGate())) {
            var gate = gateRepository.findById(flight.getGate().getId()).orElseThrow(() ->
                    new RuntimeException("Gate not found"));
            updatedFlight.setGate(gate);
        }

        flightRepository.save(updatedFlight);
        return updatedFlight;
    }

    @Override
    public void delete(Integer id) {
        var flight = flightRepository.findById(id).orElseThrow(() -> new RuntimeException("Flight not found"));
        flightRepository.delete(flight);
    }

    @Override
    public List<Flight> getAll() {
        return flightRepository.findAll();
    }
}
