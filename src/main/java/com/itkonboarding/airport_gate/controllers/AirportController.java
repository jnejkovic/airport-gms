package com.itkonboarding.airport_gate.controllers;

import com.itkonboarding.airport_gate.dto.request.AirportRequestDto;
import com.itkonboarding.airport_gate.dto.response.AirportResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for airports
 *
 * @author jnejkovic
 */
@RestController
@RequestMapping(value = "airport")
public class AirportController {

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AirportRequestDto newAirport) {
        return new ResponseEntity<AirportResponseDto>(new AirportResponseDto(), HttpStatus.CREATED);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody AirportRequestDto updateAirport) {
        return new ResponseEntity<AirportResponseDto>(new AirportResponseDto(), HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<?> get(@PathVariable Integer id) {
        return new ResponseEntity<AirportResponseDto>(new AirportResponseDto(), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return new ResponseEntity<AirportResponseDto>(new AirportResponseDto(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<List<AirportResponseDto>>(new ArrayList<AirportResponseDto>(), HttpStatus.OK);
    }

    @GetMapping(value = "{airportId}/gates")
    public ResponseEntity<?> getAllAirportGates(@PathVariable Integer airportId) {
        return new ResponseEntity<List<AirportResponseDto>>(new ArrayList<AirportResponseDto>(), HttpStatus.OK);
    }
}
