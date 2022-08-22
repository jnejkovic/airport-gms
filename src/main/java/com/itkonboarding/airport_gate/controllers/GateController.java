package com.itkonboarding.airport_gate.controllers;

import com.itkonboarding.airport_gate.dto.request.GateRequestDto;
import com.itkonboarding.airport_gate.dto.response.GateResponseDto;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

/**
 * Controller responsible for gates
 *
 * @author jnejkovic
 */
@RestController
@RequestMapping(value = "gate")
public class GateController {

    @GetMapping("{id}")
    public GateResponseDto get(@PathVariable Integer id) {
        return new GateResponseDto();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public GateResponseDto create(@RequestBody GateRequestDto newGate) {
        return new GateResponseDto();
    }

    @PutMapping(value = "{id}")
    public GateResponseDto update(@PathVariable Integer id, @RequestBody GateRequestDto newGate) {
        return new GateResponseDto();
    }

    @PutMapping(value = "{id}/available")
    public GateResponseDto updateStatus(@PathVariable Integer id) {
        return new GateResponseDto();
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Integer id) {
    }

    @PutMapping(value = "{gateId}/airport/{airportId}")
    public GateResponseDto addGateToAirport(@PathVariable Integer airportId, @PathVariable Integer gateId) {
        return new GateResponseDto();
    }
}
