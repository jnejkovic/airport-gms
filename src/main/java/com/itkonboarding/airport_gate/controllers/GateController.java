package com.itkonboarding.airport_gate.controllers;

import com.itkonboarding.airport_gate.dto.FlightDto;
import com.itkonboarding.airport_gate.dto.GateDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/gate")
public class GateController {

    @GetMapping("/{id}")
    public ResponseEntity<?> findGateById(@PathVariable Integer id) {
        return new ResponseEntity<GateDto>(new GateDto(), HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> createNewGate(@RequestBody GateDto newGate) {
        return new ResponseEntity<GateDto>(new GateDto(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{gateId}")
    public ResponseEntity<?> updateGate(@PathVariable Integer gateId, @RequestBody GateDto newGate) {
        return new ResponseEntity<GateDto>(new GateDto(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{gateId}")
    public ResponseEntity<?> deleteGateById(@PathVariable Integer gateId) {
        return new ResponseEntity<GateDto>(new GateDto(), HttpStatus.OK);
    }

    @PutMapping(value = "/{gateId}/flight/{flightId}")
    public ResponseEntity<?> addFlightToGate(@PathVariable Integer gateId, @PathVariable Integer flightId) {
        return new ResponseEntity<FlightDto>(new FlightDto(), HttpStatus.OK);
    }
}
