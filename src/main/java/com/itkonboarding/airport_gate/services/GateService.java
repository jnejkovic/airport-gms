package com.itkonboarding.airport_gate.services;

import com.itkonboarding.airport_gate.dto.request.GateRequestDto;
import com.itkonboarding.airport_gate.entities.Gate;

import java.util.Optional;

public interface GateService {

    public Optional<Gate> getById (Integer id);

    public Gate create(GateRequestDto gate);

    public Gate update(Integer id, GateRequestDto gate);

    public void delete (Integer id);
}
