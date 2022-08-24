package com.itkonboarding.airport_gate.services;

import com.itkonboarding.airport_gate.dto.request.AirportRequestDto;
import com.itkonboarding.airport_gate.entities.Airport;
import com.itkonboarding.airport_gate.repositories.AirportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AirportServiceImp implements AirportService {

    private final AirportRepository airportRepository;

    @Override
    public Optional<Airport> getById(Integer id) {
        return airportRepository.findById(id);
    }

    @Override
    public Airport create(AirportRequestDto airport) {
        Airport newAirport = new Airport();
        newAirport.setAirportName(airport.getAirportName());
        airportRepository.save(newAirport);
        return newAirport;
    }

    @Override
    public Airport update(Integer id, AirportRequestDto airport) {
        Optional<Airport> updatedAirport = airportRepository.findById(id);
        if (updatedAirport.isPresent() && airport.getAirportName() != null) {
            updatedAirport.get().setAirportName(airport.getAirportName());
        }
        airportRepository.save(updatedAirport.get());
        return updatedAirport.get();
    }

    @Override
    public void delete(Integer id) {
        Optional<Airport> airport = airportRepository.findById(id);
        if (airport.isPresent())
            airportRepository.delete(airport.get());
    }

    @Override
    public List<Airport> getAll() {
        return airportRepository.findAll();
    }
}
