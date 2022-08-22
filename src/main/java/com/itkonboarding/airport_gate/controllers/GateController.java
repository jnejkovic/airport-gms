package com.itkonboarding.airport_gate.controllers;

import com.itkonboarding.airport_gate.dto.GateDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/gate")
public class GateController {

    @GetMapping ("/{id}")
    public ResponseEntity<GateDto> getGateById (@PathVariable Integer id){
        return new ResponseEntity<GateDto>(new GateDto(), HttpStatus.OK);
    }
}
