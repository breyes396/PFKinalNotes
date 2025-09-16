package org.binaryminds.kinalnotes.web.mapper;

import org.binaryminds.kinalnotes.dominio.Role;
import org.mapstruct.Named;

public class RoleMapper {

    @Named("generarRole")
    public static Role generarRol(String rol) {

        if(rol==null) return null;

        return switch (rol.toUpperCase()){
            case "ADMINISTRADOR" -> Role.ADMIN;
            case "ESTUDIANTE" -> Role.STUDENT;
            case "DOCENTE" -> Role.TEACHER;
            default -> null;
        };
    }
    @Named("generarRol")
    public static String generarRol(Role role) {
        if(role==null) return null;
        return switch (role){
            case ADMIN -> "ADMINISTRADOR";
            case STUDENT -> "ESTUDIANTE";
            case TEACHER -> "DOCENTE";
            default -> null;
        };
    }
}

