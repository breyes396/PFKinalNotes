package org.binaryminds.kinalnotes.dominio;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record ModUsuarioDto (
        @NotBlank(message = "El nombre del usuario esta vacio")
        String nombre,
        @NotBlank(message = "El correo del usuario esta vacio")
        String correo,
     @NotBlank(message = "El contraseña del usuario esta vacio")
        String contraseña
)
{
}