package com.itkonboarding.airport_gate.dto.request;

import com.itkonboarding.airport_gate.enumerations.EStatus;
import lombok.Data;

/**
 * Dto is used to update the gate status
 *
 * @author jnejkovic
 */
@Data
public class GateStatusRequestDto {

    private EStatus status;
}
