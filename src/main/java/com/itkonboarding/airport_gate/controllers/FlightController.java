package com.itkonboarding.airport_gate.controllers;

import com.itkonboarding.airport_gate.dto.request.FlightRequestDto;
import com.itkonboarding.airport_gate.dto.request.FlightUpdateRequestDto;
import com.itkonboarding.airport_gate.dto.response.FlightResponseDto;
import com.itkonboarding.airport_gate.mappers.FlightMapper;
import com.itkonboarding.airport_gate.services.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        var flight = flightService.findById(id).map(flightMapper::flightToFlightResponseDto);
        return flight.orElseThrow(() -> new RuntimeException("Flight not found"));
    }

    /**
     * Create flight
     *
     * @param newFlight
     * @return Created flight details
     */
    @PostMapping
    @ResponseStatus(CREATED)
    public FlightResponseDto create(@RequestBody FlightRequestDto newFlight) {
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
    public FlightResponseDto update(@PathVariable Integer id, @RequestBody FlightUpdateRequestDto newFlight) {
        var flight = flightService.update(id, flightMapper.flightUpdateRequestDtoToFlight(newFlight));
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
        flightService.delete(id);
    }

    /**
     * Get all flights
     *
     * @return List of all flights
     */
    @GetMapping
    public List<FlightResponseDto> getAll() {
        return flightMapper.flightsToFlightResponseDtos(flightService.getAll());
    }

    //TODO to be done
    @PutMapping(value = "{flightId}/gate/{gateId}")
    public FlightResponseDto addFlightToGate(@PathVariable Integer gateId, @PathVariable Integer flightId) {
        return new FlightResponseDto();
    }
}
