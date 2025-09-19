package org.binaryminds.kinalnotes.dominio.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record ModEstudianteDto(
        @NotBlank(message = "El nombre es obligatorio")
        String name,
        @NotBlank(message = "El apellido es obligatorio")
        String lastname,
        List<Long> courses
) {
}
