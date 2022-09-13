package com.itkonboarding.airport_gate.controllers;

import com.itkonboarding.airport_gate.dto.request.GateRequestDto;
import com.itkonboarding.airport_gate.dto.request.GateUpdateRequestDto;
import com.itkonboarding.airport_gate.dto.response.GateResponseDto;
import com.itkonboarding.airport_gate.exceptions.ResourceNotFoundException;
import com.itkonboarding.airport_gate.mappers.GateMapper;
import com.itkonboarding.airport_gate.services.GateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        log.debug("Getting gate data for id {}", id);

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
        log.debug("Create new gate");

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
        log.debug("Update gate with id {}", id);

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
        log.debug("Change status to available for gate id {}", id);

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
        log.debug("Delete gate with id {}", id);

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
        log.debug("Get all gates for airport id {}", id);

        return gateService.getAllGatesForAirport(id).stream().map(gateMapper::gateToGateResponseDto).toList();
    }
}
