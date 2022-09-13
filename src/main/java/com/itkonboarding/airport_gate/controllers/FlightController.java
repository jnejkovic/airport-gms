package com.itkonboarding.airport_gate.controllers;

import com.itkonboarding.airport_gate.dto.request.FlightRequestDto;
import com.itkonboarding.airport_gate.dto.request.FlightUpdateRequestDto;
import com.itkonboarding.airport_gate.dto.response.FlightResponseDto;
import com.itkonboarding.airport_gate.exceptions.ResourceNotFoundException;
import com.itkonboarding.airport_gate.mappers.FlightMapper;
import com.itkonboarding.airport_gate.services.FlightService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.itkonboarding.airport_gate.exceptions.ErrorCode.FLIGHT_NOT_FOUND;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

/**
 * Controller responsible for flights
 *
 * @author jnejkovic
 */
@RestController
@RequestMapping(value = "flight")
@RequiredArgsConstructor
@Slf4j
public class FlightController {

    private final FlightService flightService;

    private final FlightMapper flightMapper;

    /**
     * Get Flight by id
     *
     * @param id
     * @return Flight details
     */
    @GetMapping("{id}")
    public FlightResponseDto get(@PathVariable Integer id) {
        log.debug("Getting flight data for id {}", id);

        return flightService.findById(id).map(flightMapper::flightToFlightResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException(FLIGHT_NOT_FOUND));
    }

    /**
     * Create flight
     *
     * @param newFlight
     * @return Created flight details
     */
    @PostMapping
    @ResponseStatus(CREATED)
    public FlightResponseDto create(@Valid @RequestBody FlightRequestDto newFlight) {
        log.debug("Create new flight with params {}", newFlight);

        var flight = flightService.create(flightMapper.flightRequestDtoToFlight(newFlight));
        return flightMapper.flightToFlightResponseDto(flight);
    }

    /**
     * Update flight
     *
     * @param id
     * @param newFlight
     * @return Updated flight details
     */
    @PutMapping(value = "{id}")
    public FlightResponseDto update(@PathVariable Integer id, @Valid @RequestBody FlightUpdateRequestDto newFlight) {
        log.debug("Update flight id {} with params {}", id, newFlight);

        var flight = flightService
                .update(id, newFlight.getGateId(), flightMapper.flightUpdateRequestDtoToFlight(newFlight));
        return flightMapper.flightToFlightResponseDto(flight);
    }

    /**
     * Delete flight
     *
     * @param id
     */
    @DeleteMapping(value = "{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        log.debug("Delete flight with id {}", id);

        flightService.delete(id);
    }

    /**
     * Get all flights
     *
     * @return List of all flights
     */
    @GetMapping
    public List<FlightResponseDto> getAll() {
        log.debug("Get all flights");

        return flightService.getAll().stream().map(flightMapper::flightToFlightResponseDto).toList();
    }
}
