package org.binaryminds.kinalnotes.dominio.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank(message = "El email es obligatorio")
        String mail,
        @NotBlank(message = "La contraseña es obligatoria")
        String password
) {
}

