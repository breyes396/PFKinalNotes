package org.binaryminds.kinalnotes.dominio.dto;

import jakarta.validation.constraints.NotBlank;

public record ModDocentesDto(

        @NotBlank(message = "El Docente del usuario esta vacio")
        String nombre,
        @NotBlank(message = "El Docente no tiene asignacion")
        String asignatura
) {
}
