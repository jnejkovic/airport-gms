package com.itkonboarding.airport_gate.controllers;

import com.itkonboarding.airport_gate.dto.FlightDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/flight")
public class FlightController {

    @GetMapping("/{flightId}")
    public ResponseEntity<?> findFlightById(@PathVariable Integer flightId) {
        return new ResponseEntity<FlightDto>(new FlightDto(), HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> createNewFlight(@RequestBody FlightDto newGate) {
        return new ResponseEntity<FlightDto>(new FlightDto(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{flightId}")
    public ResponseEntity<?> updateFlight(@PathVariable Integer flightId, @RequestBody FlightDto newFlight) {
        return new ResponseEntity<FlightDto>(new FlightDto(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{flightId}")
    public ResponseEntity<?> deleteFlightById(@PathVariable Integer flightId) {
        return new ResponseEntity<FlightDto>(new FlightDto(), HttpStatus.OK);
    }

    @GetMapping(value = "/")
    public ResponseEntity<?> getAllFlights() {
        return new ResponseEntity<List<FlightDto>>(new ArrayList<FlightDto>(), HttpStatus.OK);
    }
}
