package com.itkonboarding.airport_gate.repositories;

import com.itkonboarding.airport_gate.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
}
