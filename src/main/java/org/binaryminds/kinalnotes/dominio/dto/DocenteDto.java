package org.binaryminds.kinalnotes.dominio.dto;

import jakarta.validation.constraints.NotBlank;

public record DocenteDto(
        Long codigo,
        @NotBlank(message = "El nombre es obligatorio")
        String name,
        @NotBlank(message = "El curso es obligatorio")
        Long codigo_curso,
        Long codigo_usuario
) {
}
