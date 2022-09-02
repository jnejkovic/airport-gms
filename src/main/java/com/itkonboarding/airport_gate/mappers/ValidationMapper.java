package com.itkonboarding.airport_gate.mappers;

import com.itkonboarding.airport_gate.exceptions.ValidationFieldError;
import org.mapstruct.Mapper;
import org.springframework.validation.FieldError;

import java.util.stream.Collectors;

/**
 * Mapper used for FieldError and ValidationFieldError
 *
 * @author jnejkovic
 */
@Mapper(componentModel = "spring", imports = Collectors.class)
public interface ValidationMapper {

    ValidationFieldError errorToValidationFieldError (FieldError error);
}
