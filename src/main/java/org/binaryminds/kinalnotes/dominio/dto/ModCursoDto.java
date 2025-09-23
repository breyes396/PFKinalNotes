package org.binaryminds.kinalnotes.dominio.dto;

import jakarta.validation.constraints.NotBlank;

public record ModCursoDto(
        @NotBlank(message = "El nombre es obligatorio")
        String name



) {
}
