package com.itkonboarding.airport_gate.dto.request;

import com.itkonboarding.airport_gate.entities.Airport;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Gate Dto used for requests
 *
 * @author jnejkovic
 */
@Data
public class GateRequestDto {

    private Integer id;

    private String gateName;

    private Airport airport;
}
