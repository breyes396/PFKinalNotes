package org.binaryminds.kinalnotes.dominio.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record NotaDto(
        Long codigo,
        LocalDate date,
        @NotNull(message = "La calificacion es obligatoria")
        Integer calificacion,
        @NotNull(message = "El estudiante no puede ser nulo")
        Long codigo_estudiante,
        @NotNull(message = "El curso no puede ser nulo")
        Long codigo_curso,
        @NotNull(message = "El docente no puede ser nulo")
        Long codigo_docente
) {
}
