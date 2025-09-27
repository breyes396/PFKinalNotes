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
    private String docenteCursoNombre; // nombre del curso que imparte
    private String docenteCursoGrado; // grado/nivel del curso
    private Long estudianteCodigo; // si es estudiante

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
        docenteCursoNombre = null;
        docenteCursoGrado = null;
        estudianteCodigo = null;
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
    // Presentación combinada: Nombre (Grado) o fallback a código
    public String getDocenteCursoNombreGrado(){
        if(docenteCursoNombre == null){
            return docenteCursoCodigo != null ? String.valueOf(docenteCursoCodigo) : "";
        }
        if(docenteCursoGrado != null && !docenteCursoGrado.isBlank()){
            return docenteCursoNombre + " (" + docenteCursoGrado + ")";
        }
        return docenteCursoNombre;
    }
}
