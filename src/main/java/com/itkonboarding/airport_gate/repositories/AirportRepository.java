package com.itkonboarding.airport_gate.repositories;

import com.itkonboarding.airport_gate.entities.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Airport entity {@link Airport}
 *
 * @author jnejkovic
 */
public interface AirportRepository extends JpaRepository<Airport, Integer> {
}
