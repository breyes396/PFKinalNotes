package org.binaryminds.kinalnotes.dominio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ModCursoDto(
        @NotBlank(message = "El nombre es obligatorio")
        String name,
        @NotNull(message = "El grado o nivel es obligatorio")
        String degree,
        List<Long> students
) {
}
