package org.binaryminds.kinalnotes.dominio;


public record UsuariosDto(
    Long codigo,
    String password,
    String name,
    String mail,
    User role
) {

}


