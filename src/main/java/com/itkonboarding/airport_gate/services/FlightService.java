package com.itkonboarding.airport_gate.services;

import com.itkonboarding.airport_gate.dto.request.FlightRequestDto;
import com.itkonboarding.airport_gate.entities.Flight;

import java.util.List;
import java.util.Optional;

public interface FlightService {

    public Optional<Flight> getById (Integer id);

    public Flight create(FlightRequestDto flight);

    public Flight update(Integer id, FlightRequestDto flight);

    public void delete (Integer id);

    public List<Flight> getAll();
}
