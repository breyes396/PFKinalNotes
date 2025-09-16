package org.binaryminds.kinalnotes.dominio.dto;

import jakarta.validation.constraints.NotBlank;

public record ModDocenteDto(
        @NotBlank(message = "El nombre es obligatorio")
        String name,
        @NotBlank(message = "El curso es obligatorio")
        Integer codigo_curso
) {
}
