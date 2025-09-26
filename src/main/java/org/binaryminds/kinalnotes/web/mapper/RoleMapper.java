package org.binaryminds.kinalnotes.web.mapper;

import org.binaryminds.kinalnotes.dominio.Role;
import org.mapstruct.Named;

public class RoleMapper {

    @Named("generarRole")
    public static Role generarRol(String rol) {
        if(rol==null) return null;
        String r = rol.trim().toUpperCase();
        return switch (r) {
            case "ADMINISTRADOR", "ADMIN" -> Role.ADMIN;
            case "ESTUDIANTE", "STUDENT" -> Role.STUDENT;
            case "DOCENTE", "TEACHER" -> Role.TEACHER;
            default -> {
                try { yield Role.valueOf(r); } catch (IllegalArgumentException ex) { yield null; }
            }
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
