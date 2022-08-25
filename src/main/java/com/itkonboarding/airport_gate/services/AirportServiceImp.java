package com.itkonboarding.airport_gate.services;

import com.itkonboarding.airport_gate.dto.request.AirportRequestDto;
import com.itkonboarding.airport_gate.dto.response.AirportResponseDto;
import com.itkonboarding.airport_gate.entities.Airport;
import com.itkonboarding.airport_gate.mappers.MapStructMapper;
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

    private final MapStructMapper mapStructMapper;

    @Override
    public Optional<Airport> findById(Integer id) {
        return airportRepository.findById(id);
    }

    @Override
    public AirportResponseDto create(AirportRequestDto airport) {
        var newAirport = new Airport();
        newAirport.setAirportName(airport.getAirportName());
        airportRepository.save(newAirport);
        return mapStructMapper.airportToAirportResponseDto(newAirport);
    }

    @Override
    public AirportResponseDto update(Integer id, AirportRequestDto airport) {
        var updatedAirport = airportRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Airport not found"));
        updatedAirport.setAirportName(airport.getAirportName());
        airportRepository.save(updatedAirport);
        return mapStructMapper.airportToAirportResponseDto(updatedAirport);
    }

    @Override
    public void delete(Integer id) {
        var airport = airportRepository.findById(id).orElseThrow(() -> new RuntimeException("Airport not found"));
        airportRepository.delete(airport);
    }

    @Override
    public List<AirportResponseDto> getAll() {
        List<Airport> airports = airportRepository.findAll();
        return mapStructMapper.airportsToAirportResponseDtos(airports);
    }
}
