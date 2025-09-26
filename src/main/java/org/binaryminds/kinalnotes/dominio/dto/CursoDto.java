package org.binaryminds.kinalnotes.dominio.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CursoDto(
        Long codigo,
        @NotNull(message = "El nombre es obligatorio")
        String name,
        @NotNull(message = "El grado o nivel es obligatorio")
        String degree,
        List<Long> students
) {
}
