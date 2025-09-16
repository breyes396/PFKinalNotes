package org.binaryminds.kinalnotes.dominio.dto;

import jakarta.validation.constraints.NotBlank;
import org.binaryminds.kinalnotes.dominio.Role;

public record UsuarioDto(
        Long codigo,
        @NotBlank(message = "El email es obligatorio")
        String mail,
        @NotBlank(message = "La contraseña es obligatoria")
        String password,
        Role role
) {
}
