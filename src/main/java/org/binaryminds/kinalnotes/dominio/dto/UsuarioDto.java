package org.binaryminds.kinalnotes.dominio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.binaryminds.kinalnotes.dominio.Role;

public record UsuarioDto(
        Long codigo,
        @NotBlank(message = "El email es obligatorio")
        String mail,
        @NotBlank(message = "La contraseña es obligatoria")
        String password,
        @NotNull(message = "El rol es obligatorio (ADMIN, STUDENT o TEACHER)")
        Role role
) {
}

