package org.binaryminds.kinalnotes.dominio;

import java.time.LocalDate;

public record UsuariosDto(
    Long codigo,
    String contraseña,
    String nombre,
    String Correo,
     User rol
) {

}


