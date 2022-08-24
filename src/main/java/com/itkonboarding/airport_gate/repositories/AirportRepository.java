package com.itkonboarding.airport_gate.repositories;

import com.itkonboarding.airport_gate.entities.Airport;
import org.springframework.data.repository.CrudRepository;

public interface AirportRepository extends CrudRepository<Airport, Integer> {
}
