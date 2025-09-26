package org.binaryminds.kinalnotes.web.controller;

import jakarta.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.binaryminds.kinalnotes.dominio.Role;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.IOException;
import java.io.Serializable;

@Component
@SessionScope
@Getter
@Setter
public class AuthSession implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long usuarioId;
    private String correo;
    private Role role;
    private Long docenteCodigo; // si es docente
    private Long docenteCursoCodigo; // curso que imparte

    public boolean isLogged(){
        return usuarioId != null;
    }
    public boolean isTeacher(){
        return role == Role.TEACHER;
    }
    public boolean isStudent(){
        return role == Role.STUDENT;
    }
    public void clear(){
        usuarioId = null;
        correo = null;
        role = null;
        docenteCodigo = null;
        docenteCursoCodigo = null;
    }
    public void logout(){
        clear();
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            ctx.getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath()+"/pages/login.xhtml");
        } catch (IOException e) {
            // ignorar por simplicidad
        }
    }
}
