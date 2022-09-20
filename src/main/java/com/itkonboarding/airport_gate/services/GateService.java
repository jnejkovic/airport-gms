package com.itkonboarding.airport_gate.services;

import com.itkonboarding.airport_gate.entities.Gate;

import java.util.List;
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
    public Gate create(Integer id, Gate gate);

    /**
     * Update existing Gate entity
     *
     * @param id
     * @param gate
     * @return updated Gate entity
     */
    public Gate update(Integer gateId, Integer airportId, Gate gate);

    /**
     * Delete Gate entity by id
     *
     * @param id
     */
    public void delete(Integer id);

    /**
     * Get all airport gates
     *
     * @param id
     * @return List of airport gates
     */
    public List<Gate> getAllGatesForAirport(Integer id);

    /**
     * Set that gate is available
     *
     * @param id
     * @return Updated gate details
     */
    public Gate makeAvailable(Integer id);

    /**
     * Set gate unavailable
     *
     * @param gate
     */
    public void setUnavailable(Gate gate);

    /**
     * Check if gate is current available
     *
     * @param gate
     * @return
     */
    public boolean isGateAvailable(Gate gate);
}
