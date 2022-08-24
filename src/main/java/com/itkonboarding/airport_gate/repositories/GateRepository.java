package com.itkonboarding.airport_gate.repositories;

import com.itkonboarding.airport_gate.entities.Gate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface GateRepository extends JpaRepository<Gate, Integer> {

}
