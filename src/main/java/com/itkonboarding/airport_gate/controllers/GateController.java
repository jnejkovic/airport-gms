package com.itkonboarding.airport_gate.controllers;

import com.itkonboarding.airport_gate.dto.request.GateRequestDto;
import com.itkonboarding.airport_gate.dto.request.GateStatusRequestDto;
import com.itkonboarding.airport_gate.dto.response.GateResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for gates
 *
 * @author jnejkovic
 */
@RestController
@RequestMapping(value = "gate")
public class GateController {

    @GetMapping("{id}")
    public ResponseEntity<?> get(@PathVariable Integer id) {
        return new ResponseEntity<GateResponseDto>(new GateResponseDto(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody GateRequestDto newGate) {
        return new ResponseEntity<GateResponseDto>(new GateResponseDto(), HttpStatus.CREATED);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody GateRequestDto newGate) {
        return new ResponseEntity<GateResponseDto>(new GateResponseDto(), HttpStatus.OK);
    }

    @PutMapping(value = "{id}/available")
    public ResponseEntity<?> updateStatus(@PathVariable Integer id, @RequestBody GateStatusRequestDto newStatus) {
        return new ResponseEntity<GateResponseDto>(new GateResponseDto(), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return new ResponseEntity<GateResponseDto>(new GateResponseDto(), HttpStatus.OK);
    }

    @PutMapping(value = "{gateId}/airport/{airportId}")
    public ResponseEntity<?> addGateToAirport(@PathVariable Integer airportId, @PathVariable Integer gateId) {
        return new ResponseEntity<GateResponseDto>(new GateResponseDto(), HttpStatus.OK);
    }
}
