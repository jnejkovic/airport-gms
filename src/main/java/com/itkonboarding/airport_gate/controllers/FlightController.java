package com.itkonboarding.airport_gate.controllers;

import com.itkonboarding.airport_gate.dto.request.FlightRequestDto;
import com.itkonboarding.airport_gate.dto.response.FlightResponseDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
public class FlightController {

    @GetMapping("{id}")
    public FlightResponseDto get(@PathVariable Integer id) {
        return new FlightResponseDto();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public FlightResponseDto create(@RequestBody FlightRequestDto newGate) {
        return new FlightResponseDto();
    }

    @PutMapping(value = "{id}")
    public FlightResponseDto update(@PathVariable Integer id, @RequestBody FlightRequestDto newFlight) {
        return new FlightResponseDto();
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Integer id) {
    }

    @GetMapping
    public List<FlightResponseDto> getAll() {
        return new ArrayList<FlightResponseDto>();
    }

    @PutMapping(value = "{flightId}/gate/{gateId}")
    public FlightResponseDto addFlightToGate(@PathVariable Integer gateId, @PathVariable Integer flightId) {
        return new FlightResponseDto();
    }
}
