package com.itkonboarding.airport_gate.services;

import com.itkonboarding.airport_gate.dto.request.FlightRequestDto;
import com.itkonboarding.airport_gate.dto.request.FlightUpdateRequestDto;
import com.itkonboarding.airport_gate.entities.Flight;
import com.itkonboarding.airport_gate.repositories.FlightRepository;
import com.itkonboarding.airport_gate.repositories.GateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementation of {@link FlightService}
 */
@Service
@RequiredArgsConstructor
public class FlightServiceImp implements FlightService {

    private final FlightRepository flightRepository;

    private final GateRepository gateRepository;

    @Override
    public Optional<Flight> getById(Integer id) {
        return flightRepository.findById(id);
    }

    //TODO check if gate is available
    @Override
    public Flight create(FlightRequestDto flight) {
        var newFlight = new Flight();
        newFlight.setFlightIndex(flight.getFlightIndex());
        var gate = gateRepository.findById(flight.getGateId()).orElseThrow(() -> {
            return new RuntimeException("Gate not found");
        });
        newFlight.setGate(gate);
        flightRepository.save(newFlight);
        return newFlight;
    }

    //TODO check if gate is available
    @Override
    public Flight update(Integer id, FlightUpdateRequestDto flight) {
        var updatedFlight = flightRepository.findById(id).orElseThrow(() -> {
            return new RuntimeException("Flight not found");
        });
        if (!flight.getFlightIndex().isEmpty()) {
            updatedFlight.setFlightIndex(flight.getFlightIndex());
        }
        if (Objects.nonNull(flight.getGateId())){
            var gate = gateRepository.findById(flight.getGateId()).orElseThrow(() -> {
                return new RuntimeException("Gate not found");
            });
            updatedFlight.setGate(gate);
        }
        flightRepository.save(updatedFlight);
        return updatedFlight;
    }

    @Override
    public void delete(Integer id) {
        var flight = flightRepository.findById(id).orElseThrow(() -> {
            return new RuntimeException("Flight not found");
        });
        flightRepository.delete(flight);
    }

    @Override
    public List<Flight> getAll() {
        return flightRepository.findAll();
    }
}
