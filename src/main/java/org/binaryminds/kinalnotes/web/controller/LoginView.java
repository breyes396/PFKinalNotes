package org.binaryminds.kinalnotes.web.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.binaryminds.kinalnotes.dominio.dto.DocenteDto;
import org.binaryminds.kinalnotes.dominio.dto.UsuarioDto;
import org.binaryminds.kinalnotes.dominio.dto.EstudianteDto;
import org.binaryminds.kinalnotes.dominio.service.DocenteService;
import org.binaryminds.kinalnotes.dominio.service.UsuarioService;
import org.binaryminds.kinalnotes.dominio.service.EstudianteService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.io.IOException;
import java.io.Serializable;

@Component
@RequestScope
@Getter
@Setter
public class LoginView implements Serializable {
    private static final long serialVersionUID = 1L;

    private String email;
    private String password;

    private final UsuarioService usuarioService;
    private final AuthSession authSession; // AuthSession está en este mismo paquete
    private final DocenteService docenteService;
    private final EstudianteService estudianteService;

    public LoginView(UsuarioService usuarioService, AuthSession authSession, DocenteService docenteService, EstudianteService estudianteService) {
        this.usuarioService = usuarioService;
        this.authSession = authSession;
        this.docenteService = docenteService;
        this.estudianteService = estudianteService;
    }

    @PostConstruct
    public void init() {
        // inicialización si es necesaria
    }

    public void login() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            if (email == null || email.isBlank() || password == null || password.isBlank()) {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Campos requeridos", "Ingrese correo y contraseña"));
                return;
            }
            UsuarioDto usuario = usuarioService.obtenerUsuarioPorCorreo(email);
            if (usuario == null) {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login", "Usuario no encontrado"));
                return;
            }
            if (!usuario.password().equals(password)) {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login", "Contraseña incorrecta"));
                return;
            }
            // Poblar sesión
            authSession.setUsuarioId(usuario.codigo());
            authSession.setCorreo(usuario.mail());
            authSession.setRole(usuario.role());
            if (usuario.role() == org.binaryminds.kinalnotes.dominio.Role.TEACHER) {
                DocenteDto docente = docenteService.obtenerDocentePorCodigoUsuario(usuario.codigo());
                if (docente != null) {
                    authSession.setDocenteCodigo(docente.codigo());
                    authSession.setDocenteCursoCodigo(docente.codigo_curso());
                }
            } else if (usuario.role() == org.binaryminds.kinalnotes.dominio.Role.STUDENT) {
                EstudianteDto estudiante = estudianteService.obtenerEstudiantePorCodigoUsuario(usuario.codigo());
                if(estudiante != null){
                    authSession.setEstudianteCodigo(estudiante.codigo());
                }
            }
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Login", "Bienvenido: " + usuario.role().name()));
            // Redirección según rol
            String destino;
            switch (usuario.role()) {
                case TEACHER -> destino = "/pages/menu_docentes.xhtml";
                case STUDENT -> destino = "/pages/menu_estudiantes.xhtml";
                case ADMIN -> destino = "/pages/menu_admin.xhtml";
                default -> destino = "/index.xhtml";
            }
            ctx.getExternalContext().getFlash().setKeepMessages(true);
            ctx.getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath() + destino);
        } catch (IOException e) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", e.getMessage()));
        } catch (Exception e) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Fallo en el servidor"));
        }
    }
}
