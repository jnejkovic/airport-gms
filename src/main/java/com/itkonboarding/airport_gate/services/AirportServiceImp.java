package com.itkonboarding.airport_gate.services;

import com.itkonboarding.airport_gate.entities.Airport;
import com.itkonboarding.airport_gate.repositories.AirportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link AirportService}
 *
 * @author jnejkovic
 */
@Service
@RequiredArgsConstructor
public class AirportServiceImp implements AirportService {

    private final AirportRepository airportRepository;

    @Override
    public Optional<Airport> findById(Integer id) {
        return airportRepository.findById(id);
    }

    @Override
    public Airport create(Airport airport) {
        airportRepository.save(airport);
        return airport;
    }

    @Override
    public Airport update(Integer id, Airport airport) {
        var updatedAirport = airportRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Airport not found"));
        updatedAirport.setAirportName(airport.getAirportName());
        airportRepository.save(updatedAirport);
        return updatedAirport;
    }

    @Override
    public void delete(Integer id) {
        var airport = airportRepository.findById(id).orElseThrow(() -> new RuntimeException("Airport not found"));
        airportRepository.delete(airport);
    }

    @Override
    public List<Airport> getAll() {
        return airportRepository.findAll();
    }
}
