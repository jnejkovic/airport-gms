package com.itkonboarding.airport_gate.controllers;

import com.itkonboarding.airport_gate.dto.request.AirportRequestDto;
import com.itkonboarding.airport_gate.dto.response.AirportResponseDto;
import com.itkonboarding.airport_gate.dto.response.GateResponseDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

/**
 * Controller responsible for airports
 *
 * @author jnejkovic
 */
@RestController
@RequestMapping(value = "airport")
public class AirportController {

    @PostMapping
    @ResponseStatus(CREATED)
    public AirportResponseDto create(@RequestBody AirportRequestDto newAirport) {
        return new AirportResponseDto();
    }

    @PutMapping(value = "{id}")
    public AirportResponseDto update(@PathVariable Integer id, @RequestBody AirportRequestDto updateAirport) {
        return new AirportResponseDto();
    }

    @GetMapping(value = "{id}")
    public AirportResponseDto get(@PathVariable Integer id) {
        return new AirportResponseDto();
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Integer id) {
    }

    @GetMapping
    public List<AirportResponseDto> getAll() {
        return new ArrayList<AirportResponseDto>();
    }

    @GetMapping(value = "{airportId}/gates")
    public List<GateResponseDto> getAllAirportGates(@PathVariable Integer airportId) {
        return new ArrayList<GateResponseDto>();
    }
}
