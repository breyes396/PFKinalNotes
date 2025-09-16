package org.binaryminds.kinalnotes.dominio.dto;

import jakarta.validation.constraints.NotBlank;

public record ModUsuarioDto (
        @NotBlank(message = "El nombre del usuario esta vacio")
        String name,
        @NotBlank(message = "El correo del usuario esta vacio")
        String mail,
     @NotBlank(message = "El contraseña del usuario esta vacio")
        String password
)
{
}