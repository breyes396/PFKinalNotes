package org.binaryminds.kinalnotes.dominio.dto;

import jakarta.validation.constraints.NotBlank;

public record ModUsuarioDto(
        @NotBlank(message = "El email es obligatorio")
        String mail,
        @NotBlank(message = "La contraseña es obligatoria")
        String password
) {
}