package com.itkonboarding.airport_gate.controllers;

import com.itkonboarding.airport_gate.dto.request.AirportRequestDto;
import com.itkonboarding.airport_gate.dto.response.AirportResponseDto;
import com.itkonboarding.airport_gate.exceptions.ResourceNotFoundException;
import com.itkonboarding.airport_gate.mappers.AirportMapper;
import com.itkonboarding.airport_gate.services.AirportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.itkonboarding.airport_gate.exceptions.ErrorCode.AIRPORT_NOT_FOUND;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

/**
 * Controller responsible for airports
 *
 * @author jnejkovic
 */
@RestController
@RequestMapping(value = "airport")
@RequiredArgsConstructor
@Slf4j
public class AirportController {

    private final AirportService airportService;

    private final AirportMapper airportMapper;

    /**
     * Create airport
     *
     * @param newAirport Request data
     * @return Created airport details
     */
    @PostMapping
    @ResponseStatus(CREATED)
    public AirportResponseDto create(@Valid @RequestBody AirportRequestDto newAirport) {
        log.debug("Create new airport with params {}", newAirport);

        var airport = airportService.create(airportMapper.airportRequestDtoToAirport(newAirport));
        return airportMapper.airportToAirportResponseDto(airport);
    }

    /**
     * Update existing airport
     *
     * @param id
     * @param updateAirport
     * @return Updated airport details
     */
    @PutMapping(value = "{id}")
    public AirportResponseDto update(@PathVariable Integer id, @Valid @RequestBody AirportRequestDto updateAirport) {
        log.debug("Update airport id {} with params {}", id, updateAirport);

        var airport = airportService.update(id, airportMapper.airportRequestDtoToAirport(updateAirport));
        return airportMapper.airportToAirportResponseDto(airport);
    }

    /**
     * Get airport by id
     *
     * @param id
     * @return Airport details
     */
    @GetMapping(value = "{id}")
    public AirportResponseDto get(@PathVariable Integer id) {
        log.debug("Getting airport data for id {}", id);

        return airportService.findById(id)
                .map(airportMapper::airportToAirportResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException(AIRPORT_NOT_FOUND));
    }

    /**
     * Delete airport
     *
     * @param id
     */
    @DeleteMapping(value = "{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        log.debug("Delete airport with id {}", id);

        airportService.delete(id);
    }

    /**
     * Get all airports
     *
     * @return List of all airports
     */
    @GetMapping
    public List<AirportResponseDto> getAll() {
        log.debug("Get all airports");

        return airportService.getAll().stream().map(airportMapper::airportToAirportResponseDto).toList();
    }
}
