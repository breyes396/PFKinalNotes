package org.binaryminds.kinalnotes.dominio.dto;

import jakarta.validation.constraints.NotBlank;

public record ModNotaDto(
        @NotBlank(message = "La calificacion es obligatorio")
        String calificacion
) {
}
