package com.itkonboarding.airport_gate.services;

import com.itkonboarding.airport_gate.entities.Airport;
import com.itkonboarding.airport_gate.exceptions.NoDataFoundException;
import com.itkonboarding.airport_gate.exceptions.ResourceNotFoundException;
import com.itkonboarding.airport_gate.repositories.AirportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Airport findById(Integer id) {
        return airportRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Airport is not found for the id->" + id));
    }

    @Override
    public Airport create(Airport airport) {
        return airportRepository.save(airport);
    }

    @Override
    public Airport update(Integer id, Airport airport) {
        var updatedAirport = airportRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Airport is not found for the id->" + id));
        updatedAirport.setAirportName(airport.getAirportName());

        return airportRepository.save(updatedAirport);
    }

    @Override
    public void delete(Integer id) {
        var airport = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport is not found for the id->" + id));
        airportRepository.delete(airport);
    }

    @Override
    public List<Airport> getAll() {
        List<Airport> airports = airportRepository.findAll();

        if (airports.size() > 0) {
            return airports;
        }

        throw new NoDataFoundException("No airports data found");
    }
}
