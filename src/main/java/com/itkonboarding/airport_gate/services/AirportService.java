package com.itkonboarding.airport_gate.services;

import com.itkonboarding.airport_gate.dto.request.AirportRequestDto;
import com.itkonboarding.airport_gate.entities.Airport;

import java.util.List;
import java.util.Optional;

public interface AirportService {

    public Optional<Airport> getById(Integer id);

    public Airport create(AirportRequestDto airport);

    public Airport update(Integer id, AirportRequestDto airport);

    public void delete (Integer id);

    public List<Airport> getAll();
}
