package com.itkonboarding.airport_gate.repositories;

import com.itkonboarding.airport_gate.entities.Gate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Gate entity {@link Gate}
 *
 * @author jnejkovic
 */
public interface GateRepository extends JpaRepository<Gate, Integer> {

}
