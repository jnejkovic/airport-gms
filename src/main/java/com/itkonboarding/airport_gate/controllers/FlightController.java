package com.itkonboarding.airport_gate.controllers;

import com.itkonboarding.airport_gate.dto.request.FlightRequestDto;
import com.itkonboarding.airport_gate.dto.response.FlightResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for flights
 *
 * @author jnejkovic
 */
@RestController
@RequestMapping(value = "flight")
public class FlightController {

    @GetMapping("{id}")
    public ResponseEntity<?> get(@PathVariable Integer id) {
        return new ResponseEntity<FlightResponseDto>(new FlightResponseDto(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody FlightRequestDto newGate) {
        return new ResponseEntity<FlightResponseDto>(new FlightResponseDto(), HttpStatus.CREATED);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody FlightRequestDto newFlight) {
        return new ResponseEntity<FlightResponseDto>(new FlightResponseDto(), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return new ResponseEntity<FlightResponseDto>(new FlightResponseDto(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<List<FlightResponseDto>>(new ArrayList<FlightResponseDto>(), HttpStatus.OK);
    }

    @PutMapping(value = "{flightId}/gate/{gateId}")
    public ResponseEntity<?> addFlightToGate(@PathVariable Integer gateId, @PathVariable Integer flightId) {
        return new ResponseEntity<FlightResponseDto>(new FlightResponseDto(), HttpStatus.OK);
    }
}
