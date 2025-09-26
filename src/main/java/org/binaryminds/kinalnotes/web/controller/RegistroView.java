package org.binaryminds.kinalnotes.web.controller;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.binaryminds.kinalnotes.dominio.Role;
import org.binaryminds.kinalnotes.dominio.dto.*;
import org.binaryminds.kinalnotes.dominio.service.CursoService;
import org.binaryminds.kinalnotes.dominio.service.DocenteService;
import org.binaryminds.kinalnotes.dominio.service.EstudianteService;
import org.binaryminds.kinalnotes.dominio.service.UsuarioService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Component
@ViewScoped
@Getter
@Setter
public class RegistroView implements Serializable {
    private static final long serialVersionUID = 1L;

    // Datos de usuario
    private String email;
    private String password;
    private Role role; // STUDENT o TEACHER

    // Datos específicos
    private String nombre;
    private String apellido; // solo estudiante
    private Long cursoSeleccionado; // solo docente

    private List<CursoDto> cursos;
    private List<CursoDto> cursosDisponiblesDocente; // cursos sin docente
    private List<SelectItem> cursosSelectDocente;    // para el combo (evita acceso directo a record)
    private List<Role> rolesPermitidos;

    private final UsuarioService usuarioService;
    private final EstudianteService estudianteService;
    private final DocenteService docenteService;
    private final CursoService cursoService;

    public RegistroView(UsuarioService usuarioService,
                        EstudianteService estudianteService,
                        DocenteService docenteService,
                        CursoService cursoService) {
        this.usuarioService = usuarioService;
        this.estudianteService = estudianteService;
        this.docenteService = docenteService;
        this.cursoService = cursoService;
        init();
    }

    public void init() {
        this.rolesPermitidos = Arrays.stream(Role.values())
                .filter(r -> r != Role.ADMIN)
                .collect(Collectors.toList());
        this.cursos = cursoService.obtenerTodo();
        filtrarCursosDisponibles();
    }

    private void filtrarCursosDisponibles(){
        try {
            Set<Long> cursosOcupados = docenteService.obtenerTodo().stream()
                    .map(DocenteDto::codigo_curso)
                    .collect(Collectors.toSet());
            this.cursosDisponiblesDocente = this.cursos.stream()
                    .filter(c -> !cursosOcupados.contains(c.codigo()))
                    .collect(Collectors.toList());
        } catch (Exception e){
            this.cursosDisponiblesDocente = this.cursos != null ? this.cursos : List.of();
        }
        construirSelectCursos();
    }

    private void construirSelectCursos(){
        this.cursosSelectDocente = this.cursosDisponiblesDocente.stream()
                .map(c -> new SelectItem(c.codigo(), c.name() + " (" + c.degree() + ")"))
                .collect(Collectors.toList());
    }

    public String roleLabel(Role r){
        return switch (r){
            case STUDENT -> "Estudiante";
            case TEACHER -> "Docente";
            default -> r.name();
        };
    }

    public boolean isStudent() { return role == Role.STUDENT; }
    public boolean isTeacher() { return role == Role.TEACHER; }

    public void registrar() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            if (email == null || email.isBlank() || password == null || password.isBlank() || role == null) {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Validación", "Complete correo, contraseña y rol"));
                return;
            }
            if (isStudent()) {
                if (nombre == null || nombre.isBlank() || apellido == null || apellido.isBlank()) {
                    ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Validación", "Nombre y apellido requeridos"));
                    return;
                }
            } else if (isTeacher()) {
                if (nombre == null || nombre.isBlank() || cursoSeleccionado == null) {
                    ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Validación", "Nombre y curso requeridos"));
                    return;
                }
            }
            // Crear usuario
            UsuarioDto nuevo = usuarioService.guardarUsuario(new UsuarioDto(null, email, password, role));
            Long codigoUsuario = nuevo.codigo();

            if (isStudent()) {
                estudianteService.guardarEstudiante(new EstudianteDto(null, nombre, apellido, List.of(), codigoUsuario));
            } else if (isTeacher()) {
                docenteService.guardarDocente(new DocenteDto(null, nombre, cursoSeleccionado, codigoUsuario));
            }
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro", "Registro exitoso. Inicie sesión"));
            ctx.getExternalContext().getFlash().setKeepMessages(true);
            ctx.getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath() + "/pages/login.xhtml");
        } catch (IOException e) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", e.getMessage()));
        } catch (Exception e) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "No se pudo completar el registro"));
        }
    }
}
