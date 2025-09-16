package org.binaryminds.kinalnotes.dominio.dto;


import org.binaryminds.kinalnotes.dominio.Role;

public record UsuariosDto(
    Long codigo,
    String password,
    String name,
    String mail,
    Role role
) {

}


