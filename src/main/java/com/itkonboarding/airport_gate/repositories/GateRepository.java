package com.itkonboarding.airport_gate.repositories;

import com.itkonboarding.airport_gate.entities.Gate;
import org.springframework.data.repository.CrudRepository;

public interface GateRepository extends CrudRepository<Gate, Integer> {
}
