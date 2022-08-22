package com.itkonboarding.airport_gate.controllers;

import com.itkonboarding.airport_gate.dto.AirportDto;
import com.itkonboarding.airport_gate.dto.GateDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "airport")
public class AirportController {

    @PostMapping(value = "/")
    public ResponseEntity<?> createNewAirport(@RequestBody AirportDto newAirport) {
        return new ResponseEntity<AirportDto>(new AirportDto(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{airportId}")
    public ResponseEntity<?> updateAirport(@PathVariable Integer airportId, @RequestBody AirportDto updateAirport) {
        return new ResponseEntity<AirportDto>(new AirportDto(), HttpStatus.OK);
    }

    @GetMapping(value = "/{airportId}")
    public ResponseEntity<?> findAirportById(@PathVariable Integer airportId) {
        return new ResponseEntity<AirportDto>(new AirportDto(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{airportId}")
    public ResponseEntity<?> deleteAirportById(@PathVariable Integer airportId) {
        return new ResponseEntity<AirportDto>(new AirportDto(), HttpStatus.OK);
    }

    @GetMapping(value = "/")
    public ResponseEntity<?> getAllAirports() {
        return new ResponseEntity<List<AirportDto>>(new ArrayList<AirportDto>(), HttpStatus.OK);
    }

    @PutMapping(value = "/{airportId}/gate/{gateId}")
    public ResponseEntity<?> addGateToAirport(@PathVariable Integer airportId, @PathVariable Integer gateId) {
        return new ResponseEntity<GateDto>(new GateDto(), HttpStatus.OK);
    }

    @GetMapping(value = "/{airportId}/gates")
    public ResponseEntity<?> getAllAirportGates(@PathVariable Integer airportId) {
        return new ResponseEntity<List<AirportDto>>(new ArrayList<AirportDto>(), HttpStatus.OK);
    }
}
