package org.binaryminds.kinalnotes.dominio.dto;

import jakarta.validation.constraints.NotNull;

public record ModNotaDto(
        @NotNull(message = "La calificacion es obligatoria")
        Integer calificacion,
        @NotNull(message = "El estudiante no puede ser nulo")
        Long codigo_estudiante
) {
}
