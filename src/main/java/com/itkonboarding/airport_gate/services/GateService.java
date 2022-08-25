package com.itkonboarding.airport_gate.services;

import com.itkonboarding.airport_gate.dto.request.GateRequestDto;
import com.itkonboarding.airport_gate.dto.request.GateUpdateRequestDto;
import com.itkonboarding.airport_gate.dto.response.GateResponseDto;
import com.itkonboarding.airport_gate.entities.Gate;

import java.util.Optional;

/**
 * Service used for gate related operations
 *
 * @author jnejkovic
 */
public interface GateService {

    /**
     * Get Gate entity by id
     *
     * @param id
     * @return Gate entity if exists
     */
    public Optional<Gate> findById(Integer id);

    /**
     * Create new Gate entity
     *
     * @param gate
     * @return created Gate entity
     */
    public Gate create(Gate gate);

    /**
     * Update existing Gate entity
     *
     * @param id
     * @param gate
     * @return updated Gate entity
     */
    public Gate update( Integer id, Gate gate);

    /**
     * Delete Gate entity by id
     *
     * @param id
     */
    public void delete(Integer id);
}
