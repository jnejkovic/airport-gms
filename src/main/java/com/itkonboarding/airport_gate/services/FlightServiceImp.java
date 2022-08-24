package com.itkonboarding.airport_gate.services;

import com.itkonboarding.airport_gate.dto.request.FlightRequestDto;
import com.itkonboarding.airport_gate.entities.Flight;
import com.itkonboarding.airport_gate.entities.Gate;
import com.itkonboarding.airport_gate.repositories.FlightRepository;
import com.itkonboarding.airport_gate.repositories.GateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlightServiceImp implements FlightService {

    private final FlightRepository flightRepository;

    private final GateRepository gateRepository;

    @Override
    public Optional<Flight> getById(Integer id) {
        return flightRepository.findById(id);
    }

    @Override
    public Flight create(FlightRequestDto flight) {
        Flight newFlight = new Flight();
        newFlight.setFlightIndex(flight.getFlightIndex());
        Optional<Gate> gate = gateRepository.findById(flight.getGateId());
        if (gate.isPresent())
            newFlight.setGate(gate.get());
        flightRepository.save(newFlight);
        return newFlight;
    }

    @Override
    public Flight update(Integer id, FlightRequestDto flight) {
        Optional<Flight> updatedFlight = flightRepository.findById(id);
        if (updatedFlight.isPresent()) {
            if (flight.getFlightIndex() != null) {
                updatedFlight.get().setFlightIndex(flight.getFlightIndex());
            }
            if (flight.getGateId() != null) {
                Optional<Gate> gate = gateRepository.findById(flight.getGateId());
                if (gate.isPresent()) {
                    updatedFlight.get().setGate(gate.get());
                }
            }
        }
        flightRepository.save(updatedFlight.get());
        return updatedFlight.get();
    }

    @Override
    public void delete(Integer id) {
        Optional<Flight> flight = flightRepository.findById(id);
        if (flight.isPresent())
            flightRepository.delete(flight.get());
    }

    @Override
    public List<Flight> getAll() {
        return flightRepository.findAll();
    }
}
