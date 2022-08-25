package com.itkonboarding.airport_gate.controllers;

import com.itkonboarding.airport_gate.dto.request.GateRequestDto;
import com.itkonboarding.airport_gate.dto.request.GateUpdateRequestDto;
import com.itkonboarding.airport_gate.dto.response.GateResponseDto;
import com.itkonboarding.airport_gate.mappers.GateMapper;
import com.itkonboarding.airport_gate.services.GateService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class GateController {

    private final GateService gateService;

    private final GateMapper gateMapper;

    /**
     * Get gate by id
     *
     * @param id
     * @return Gate details
     */
    @GetMapping("{id}")
    public GateResponseDto get(@PathVariable Integer id) {
        var gate = gateService.findById(id).map(gateMapper::gateToGateResponseDto);
        return gate.orElseThrow(() -> new RuntimeException("Gate not found"));
    }

    /**
     * Create gate
     *
     * @param newGate
     * @return Created gate details
     */
    @PostMapping
    @ResponseStatus(CREATED)
    public GateResponseDto create(@RequestBody GateRequestDto newGate) {
        var gate = gateService.create(gateMapper.gateRequestDtoToGate(newGate));
        return gateMapper.gateToGateResponseDto(gate);
    }

    /**
     * Update gate
     *
     * @param id
     * @param newGate
     * @return Updated gate details
     */
    @PutMapping(value = "{id}")
    public GateResponseDto update(@PathVariable Integer id, @RequestBody GateUpdateRequestDto newGate) {
        var gate = gateService.update(id, gateMapper.gateUpdateRequestDtoToGate(newGate));
        return gateMapper.gateToGateResponseDto(gate);
    }

    //TODO to be done
    @PutMapping(value = "{id}/available")
    public GateResponseDto updateStatus(@PathVariable Integer id) {
        return new GateResponseDto();
    }

    /**
     * Delete gate
     *
     * @param id
     */
    @DeleteMapping(value = "{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        gateService.delete(id);
    }

    //TODO to be done
    @PutMapping(value = "{gateId}/airport/{airportId}")
    public GateResponseDto addGateToAirport(@PathVariable Integer airportId, @PathVariable Integer gateId) {
        return new GateResponseDto();
    }
}
