package org.binaryminds.kinalnotes.dominio.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record EstudianteDto(
        Long codigo,
        @NotBlank(message = "El nombre es obligatorio")
        String name,
        @NotBlank(message = "El apellido es obligatorio")
        String lastname,
        List<Long> courses,
        @NotBlank(message = "El estudiante debe tener un usuario")
        Long codigo_usuario
) {
}
