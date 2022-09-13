package com.itkonboarding.airport_gate.services;

import com.itkonboarding.airport_gate.entities.Airport;
import com.itkonboarding.airport_gate.exceptions.ResourceNotFoundException;
import com.itkonboarding.airport_gate.repositories.AirportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;

    @Override
    public Optional<Airport> findById(Integer id) {
        log.debug("Find airport with id {}", id);

        return airportRepository.findById(id);
    }

    @Override
    public Airport create(Airport airport) {
        log.debug("Create new airport with params {}", airport);

        return airportRepository.save(airport);
    }

    @Override
    public Airport update(Integer id, Airport airport) {
        log.debug("Update airport id {} with params", airport);

        var updatedAirport = airportRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(AIRPORT_NOT_FOUND));
        updatedAirport.setAirportName(airport.getAirportName());

        return airportRepository.save(updatedAirport);
    }

    @Override
    public void delete(Integer id) {
        log.debug("Delete airport with id {}", id);

        var airport = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AIRPORT_NOT_FOUND));
        airportRepository.delete(airport);
    }

    @Override
    public List<Airport> getAll() {
        log.debug("Find all airports");
        return airportRepository.findAll();
    }
}
