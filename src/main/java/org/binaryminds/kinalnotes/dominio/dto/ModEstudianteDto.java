package org.binaryminds.kinalnotes.dominio.dto;

import jakarta.validation.constraints.NotBlank;

public record ModEstudianteDto(
        @NotBlank(message = "El nombre es obligatorio")
        String name,
        @NotBlank(message = "El curso es obligatorio")
        Integer cursos,
        @NotBlank(message = "El apellido es obligatorio")
        Integer lastname

) {
}
