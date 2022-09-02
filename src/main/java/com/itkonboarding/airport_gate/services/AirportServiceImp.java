package com.itkonboarding.airport_gate.services;

import com.itkonboarding.airport_gate.entities.Airport;
import com.itkonboarding.airport_gate.exceptions.ResourceNotFoundException;
import com.itkonboarding.airport_gate.repositories.AirportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.itkonboarding.airport_gate.exceptions.ErrorCode.AIRPORT_NOT_FOUND;

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
        return airportRepository.save(airport);
    }

    @Override
    public Airport update(Integer id, Airport airport) {
        var updatedAirport = airportRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(AIRPORT_NOT_FOUND));
        updatedAirport.setAirportName(airport.getAirportName());

        return airportRepository.save(updatedAirport);
    }

    @Override
    public void delete(Integer id) {
        var airport = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AIRPORT_NOT_FOUND));
        airportRepository.delete(airport);
    }

    @Override
    public List<Airport> getAll() {
        return airportRepository.findAll();
    }
}
