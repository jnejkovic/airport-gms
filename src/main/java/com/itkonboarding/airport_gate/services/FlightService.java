package com.itkonboarding.airport_gate.services;

import com.itkonboarding.airport_gate.dto.request.FlightRequestDto;
import com.itkonboarding.airport_gate.dto.request.FlightUpdateRequestDto;
import com.itkonboarding.airport_gate.dto.response.FlightResponseDto;
import com.itkonboarding.airport_gate.entities.Flight;

import java.util.List;
import java.util.Optional;

/**
 * Service used for flight related operations
 *
 * @author jnejkovic
 */
public interface FlightService {

    /**
     * Get Flight entity by id
     *
     * @param id
     * @return Flight entity if exists
     */
    public Optional<Flight> findById(Integer id);

    /**
     * Create new Flight entity
     *
     * @param flight
     * @return created Flight entity
     */
    public FlightResponseDto create(FlightRequestDto flight);

    /**
     * Update existing Flight entity
     *
     * @param id
     * @param flight
     * @return updated Flight entity
     */
    public FlightResponseDto update(Integer id, FlightUpdateRequestDto flight);

    /**
     * Delete existing Flight entity
     *
     * @param id
     */
    public void delete(Integer id);

    /**
     * Get all Flight entities
     *
     * @return list of Flight entites
     */
    public List<FlightResponseDto> getAll();
}
