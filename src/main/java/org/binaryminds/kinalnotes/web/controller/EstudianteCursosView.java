package org.binaryminds.kinalnotes.web.controller;

import jakarta.annotation.PostConstruct;
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
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@ViewScoped
@Getter
@Setter
public class EstudianteCursosView implements Serializable {
    private static final long serialVersionUID = 1L;

    private final AuthSession authSession;
    private final EstudianteService estudianteService;
    private final CursoService cursoService;

    private List<CursoRow> cursosInscritos;

    public record CursoRow(Long codigo, String nombre, String grado) { }

    public EstudianteCursosView(AuthSession authSession, EstudianteService estudianteService, CursoService cursoService) {
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
        cargarCursos();
    }

    public void cargarCursos(){
        cursosInscritos = new ArrayList<>();
        Long estId = authSession.getEstudianteCodigo();
        if(estId == null) return;
        EstudianteDto est = estudianteService.obtenerEstudiantePorId(estId);
        if(est == null || est.courses()==null) return;
        List<Long> codigos = est.courses();
        List<CursoDto> todos = cursoService.obtenerTodo();
        cursosInscritos = todos.stream()
                .filter(c -> codigos.contains(c.codigo()))
                .map(c -> new CursoRow(c.codigo(), c.name(), c.degree()))
                .collect(Collectors.toList());
        log.debug("Cursos inscritos cargados: {}", cursosInscritos.size());
    }
}

