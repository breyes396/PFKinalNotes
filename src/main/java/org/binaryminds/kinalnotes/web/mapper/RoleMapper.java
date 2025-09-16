package org.binaryminds.kinalnotes.web.mapper;

import org.binaryminds.kinalnotes.dominio.User;
import org.mapstruct.Named;

public class RoleMapper {
    @Named("generarRole")
    public static User generarRol(String rol) {
        if(rol==null) return null;
        return switch (rol.toUpperCase()){
            case "ADMINISTRADOR" -> User.ADMIN;
            case "ESTUDIANTE" -> User.STUDENT;
            case "DOCENTE" -> User.THEACHING;
            default -> null;
        };
    }
    @Named("generarRol")
    public static String generarRol(User role) {
        if(role==null) return null;
        return switch (role){
            case ADMIN -> "ADMINISTRADOR";
            case STUDENT -> "ESTUDIANTE";
            case THEACHING -> "DOCENTE";
            default -> null;
        };
    }
}

