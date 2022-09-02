package com.itkonboarding.airport_gate.controllers;

import com.itkonboarding.airport_gate.dto.request.GateRequestDto;
import com.itkonboarding.airport_gate.dto.request.GateUpdateRequestDto;
import com.itkonboarding.airport_gate.dto.response.GateResponseDto;
import com.itkonboarding.airport_gate.exceptions.ResourceNotFoundException;
import com.itkonboarding.airport_gate.mappers.GateMapper;
import com.itkonboarding.airport_gate.services.GateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.itkonboarding.airport_gate.exceptions.ErrorCode.GATE_NOT_FOUND;
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
        return gateService.findById(id).map(gateMapper::gateToGateResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException(GATE_NOT_FOUND));
    }

    /**
     * Create gate
     *
     * @param newGate
     * @return Created gate details
     */
    @PostMapping
    @ResponseStatus(CREATED)
    public GateResponseDto create(@Valid @RequestBody GateRequestDto newGate) {
        var gate = gateService.create(newGate.getAirportId(), gateMapper.gateRequestDtoToGate(newGate));
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
    public GateResponseDto update(@PathVariable Integer id, @Valid @RequestBody GateUpdateRequestDto newGate) {
        var gate = gateService.update(id, newGate.getAirportId(), gateMapper.gateUpdateRequestDtoToGate(newGate));
        return gateMapper.gateToGateResponseDto(gate);
    }

    /**
     * Set that gate is available
     *
     * @param id
     * @return Updated gate details
     */
    @PutMapping(value = "{id}/available")
    public GateResponseDto makeAvailable(@PathVariable Integer id) {
        return gateMapper.gateToGateResponseDto(gateService.makeAvailable(id));
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

    /**
     * Get all airport gates
     *
     * @param id
     * @return List of all airport gates
     */
    @GetMapping(value = "{id}/gates")
    public List<GateResponseDto> getAllGatesForAirport(@PathVariable Integer id) {
        return gateService.getAllGatesForAirport(id).stream().map(gateMapper::gateToGateResponseDto).toList();
    }
}
