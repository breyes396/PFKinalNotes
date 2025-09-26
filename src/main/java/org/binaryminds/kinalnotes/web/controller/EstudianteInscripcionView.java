package org.binaryminds.kinalnotes.web.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.binaryminds.kinalnotes.dominio.dto.CursoDto;
import org.binaryminds.kinalnotes.dominio.dto.EstudianteDto;
import org.binaryminds.kinalnotes.dominio.service.CursoService;
import org.binaryminds.kinalnotes.dominio.service.EstudianteService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@ViewScoped
@Getter
@Setter
public class EstudianteInscripcionView implements Serializable {
    private static final long serialVersionUID = 1L;

    private final AuthSession authSession;
    private final EstudianteService estudianteService;
    private final CursoService cursoService;

    public record CursoRow(Long codigo, String nombre, String grado) implements Serializable { private static final long serialVersionUID = 1L; }

    private List<CursoRow> cursosDisponibles;

    public EstudianteInscripcionView(AuthSession authSession, EstudianteService estudianteService, CursoService cursoService) {
        this.authSession = authSession;
        this.estudianteService = estudianteService;
        this.cursoService = cursoService;
    }

    @PostConstruct
    public void init(){
        FacesContext ctx = FacesContext.getCurrentInstance();
        if(!authSession.isLogged() || !authSession.isStudent()){
            try { ctx.getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath()+"/pages/login.xhtml"); } catch (IOException ignored) {}
            return;
        }
        cargarCursosDisponibles();
    }

    public void cargarCursosDisponibles(){
        cursosDisponibles = new ArrayList<>();
        Long estId = authSession.getEstudianteCodigo();
        if(estId == null) return;
        EstudianteDto est = estudianteService.obtenerEstudiantePorId(estId);
        if(est == null){
            log.warn("Estudiante con id {} no encontrado al cargar cursos disponibles", estId);
            return;
        }
        Set<Long> actuales = est.courses() != null ? new HashSet<>(est.courses()) : Set.of();
        for(CursoDto c : cursoService.obtenerTodo()){
            if(!actuales.contains(c.codigo())){
                cursosDisponibles.add(new CursoRow(c.codigo(), c.name(), c.degree()));
            }
        }
        log.debug("Cursos disponibles para inscripción (estudiante={}): {}", estId, cursosDisponibles.size());
    }

    public void inscribir(Long cursoId){
        FacesContext ctx = FacesContext.getCurrentInstance();
        Long estId = authSession.getEstudianteCodigo();
        if(estId == null || cursoId == null){
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Inscripción", "Datos inválidos"));
            return;
        }
        EstudianteDto est = estudianteService.obtenerEstudiantePorId(estId);
        if(est == null){
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Inscripción", "Estudiante no existe"));
            return;
        }
        // Validar si ya estaba inscrito (lista del estudiante)
        if(est.courses() != null && est.courses().contains(cursoId)){
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Inscripción", "Ya estás inscrito en el curso " + cursoId));
            cargarCursosDisponibles();
            return;
        }
        // Validar que el curso figure en la lista de disponibles (defensa frente a manipulación de parámetros)
        boolean cursoEnDisponibles = cursosDisponibles != null && cursosDisponibles.stream().anyMatch(c -> c.codigo().equals(cursoId));
        if(!cursoEnDisponibles){
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Inscripción", "El curso no está disponible para inscripción"));
            return;
        }
        try {
            log.info("Intentando inscribir estudiante {} en curso {}", estId, cursoId);
            estudianteService.inscribirEstudianteEnCurso(estId, cursoId);
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Inscripción", "Inscripción exitosa al curso " + cursoId));
            cargarCursosDisponibles();
        } catch (IllegalArgumentException iae){
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Inscripción", iae.getMessage()));
        } catch (Exception ex){
            log.error("Error inscribiendo al curso (estudiante={}, curso={})", estId, cursoId, ex);
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Inscripción", "No se pudo inscribir. Intente de nuevo"));
        }
    }
}
