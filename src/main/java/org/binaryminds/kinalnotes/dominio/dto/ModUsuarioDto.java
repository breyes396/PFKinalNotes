package org.binaryminds.kinalnotes.dominio.dto;

import jakarta.validation.constraints.NotBlank;

public record ModUsuarioDto (
        @NotBlank(message = "El nombre del usuario esta vacio")
        String nombre,
        @NotBlank(message = "El correo del usuario esta vacio")
        String correo,
     @NotBlank(message = "El contraseña del usuario esta vacio")
        String contrasena
)
{
}