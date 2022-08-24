package com.itkonboarding.airport_gate.services;

import com.itkonboarding.airport_gate.dto.request.AirportRequestDto;
import com.itkonboarding.airport_gate.entities.Airport;

import java.util.List;
import java.util.Optional;

/**
 * Service used for airport related operation
 *
 * @author jnejkovic
 */
public interface AirportService {

    /**
     * Get Airport entity by id
     *
     * @param id
     * @return Airport entity if exists
     */
    public Optional<Airport> findById(Integer id);

    /**
     * Create new airport
     *
     * @param airport
     * @return created Airport entity
     */
    public Airport create(AirportRequestDto airport);

    /**
     * Update existing Airport entity
     *
     * @param id
     * @param airport
     * @return updated Airport entity
     */
    public Airport update(Integer id, AirportRequestDto airport);

    /**
     * Delete Airport entity
     *
     * @param id
     */
    public void delete(Integer id);

    /**
     * List all Airport entities
     *
     * @return list of all Airport entities
     */
    public List<Airport> getAll();
}
