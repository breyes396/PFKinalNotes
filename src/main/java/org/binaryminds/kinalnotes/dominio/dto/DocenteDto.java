package org.binaryminds.kinalnotes.dominio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DocenteDto(
        Long codigo,
        @NotBlank(message = "El nombre es obligatorio")
        String name,
        @NotNull(message = "El curso es obligatorio")
        Long codigo_curso,
        @NotBlank(message = "El docente debe tener un usuario")
        Long codigo_usuario
) {
}
