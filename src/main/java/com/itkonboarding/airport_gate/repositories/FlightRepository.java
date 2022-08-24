package com.itkonboarding.airport_gate.repositories;

import com.itkonboarding.airport_gate.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Flight entity {@link Flight}
 *
 * @author jnejkovic
 */
public interface FlightRepository extends JpaRepository<Flight, Integer> {
}
