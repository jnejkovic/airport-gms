package com.itkonboarding.airport_gate.services;

import com.itkonboarding.airport_gate.dto.request.AirportRequestDto;
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
    public Airport create(AirportRequestDto airport) {
        var newAirport = new Airport();
        newAirport.setAirportName(airport.getAirportName());
        airportRepository.save(newAirport);
        return newAirport;
    }

    @Override
    public Airport update(Integer id, AirportRequestDto airport) {
        var updatedAirport = airportRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Airport not found"));
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
