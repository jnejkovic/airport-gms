package com.itkonboarding.airport_gate.services;

import com.itkonboarding.airport_gate.dto.request.GateRequestDto;
import com.itkonboarding.airport_gate.entities.Airport;
import com.itkonboarding.airport_gate.entities.Gate;
import com.itkonboarding.airport_gate.repositories.AirportRepository;
import com.itkonboarding.airport_gate.repositories.GateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        Gate newGate = new Gate();
        newGate.setGateName(gate.getGateName());
        Optional<Airport> airport = airportRepository.findById(gate.getAirportId());
        if (airport.isPresent())
            newGate.setAirport(airport.get());
        gateRepository.save(newGate);
        return newGate;
    }

    @Override
    public Gate update(Integer id, GateRequestDto gate) {
        Optional<Gate> updatedGate = gateRepository.findById(id);
        if (updatedGate.isPresent()) {
            if (gate.getGateName() != null) {
                updatedGate.get().setGateName(gate.getGateName());
            }
            if (gate.getAirportId() != null) {
                Optional<Airport> airport = airportRepository.findById(gate.getAirportId());
                if (airport.isPresent()) {
                    updatedGate.get().setAirport(airport.get());
                }
            }
        }
        gateRepository.save(updatedGate.get());
        return updatedGate.get();
    }

    @Override
    public void delete(Integer id) {
        Optional<Gate> gate = gateRepository.findById(id);
        if (gate.isPresent())
            gateRepository.delete(gate.get());
    }
}
