package org.binaryminds.kinalnotes.dominio.dto;


import org.binaryminds.kinalnotes.dominio.User;

public record UsuariosDto(
    Long codigo,
    String name,
    String mail,
    User role
) {

}


