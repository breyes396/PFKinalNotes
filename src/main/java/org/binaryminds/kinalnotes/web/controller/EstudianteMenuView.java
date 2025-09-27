package org.binaryminds.kinalnotes.web.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.binaryminds.kinalnotes.dominio.dto.CursoDto;
import org.binaryminds.kinalnotes.dominio.dto.DocenteDto;
import org.binaryminds.kinalnotes.dominio.dto.NotaDto;
import org.binaryminds.kinalnotes.dominio.service.CursoService;
import org.binaryminds.kinalnotes.dominio.service.DocenteService;
import org.binaryminds.kinalnotes.dominio.service.EstudianteService;
import org.binaryminds.kinalnotes.dominio.service.NotaService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

@Component
@ViewScoped
@Getter
@Setter
@Slf4j
public class EstudianteMenuView implements Serializable {
    private static final long serialVersionUID = 1L;

    private final AuthSession authSession;
    private final EstudianteService estudianteService;
    private final CursoService cursoService; // por si se requiere detalle
    private final DocenteService docenteService;
    private final NotaService notaService;

    // Datos para UI
    private List<CursoRow> cursosInscritos; // con nota
    private List<CursoRow> cursosDisponibles; // todos envueltos en CursoRow para compatibilidad EL
    private Map<Long, Integer> notasPorCurso;

    @Getter
    @Setter
    public static class CursoRow implements Serializable {
        private Long codigo;
        private String nombre;
        private String grado;
        private Integer nota; // null si no existe
        public CursoRow(Long codigo, String nombre, String grado, Integer nota){
            this.codigo = codigo;
            this.nombre = nombre;
            this.grado = grado;
            this.nota = nota;
        }
    }

    public EstudianteMenuView(AuthSession authSession, EstudianteService estudianteService, CursoService cursoService, DocenteService docenteService, NotaService notaService) {
        this.authSession = authSession;
        this.estudianteService = estudianteService;
        this.cursoService = cursoService;
        this.docenteService = docenteService;
        this.notaService = notaService;
    }

    @PostConstruct
    public void init(){
        FacesContext ctx = FacesContext.getCurrentInstance();
        if(!authSession.isLogged() || !authSession.isStudent()){
            try {
                ctx.getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath()+"/pages/login.xhtml");
            } catch (IOException ignored) {}
            return;
        }
        cargarDatos();
    }

    public void cargarDatos(){
        Long estudianteCodigo = authSession.getEstudianteCodigo();
        notasPorCurso = new HashMap<>();
        cursosInscritos = new ArrayList<>();
        List<CursoDto> inscritos = estudianteService.obtenerCursosInscritos(estudianteCodigo);
        for(CursoDto c : inscritos){
            Integer nota = obtenerNotaCurso(estudianteCodigo, c.codigo());
            cursosInscritos.add(new CursoRow(c.codigo(), c.name(), c.degree(), nota));
            if(nota != null){
                notasPorCurso.put(c.codigo(), nota);
            }
        }
        List<CursoDto> disponibles = estudianteService.obtenerCursosDisponibles(estudianteCodigo);
        cursosDisponibles = new ArrayList<>();
        for(CursoDto c : disponibles){
            cursosDisponibles.add(new CursoRow(c.codigo(), c.name(), c.degree(), notasPorCurso.get(c.codigo())));
        }
        log.debug("Datos cargados: inscritos={} disponibles={}", cursosInscritos.size(), cursosDisponibles.size());
    }

    private Integer obtenerNotaCurso(Long estudianteCodigo, Long cursoCodigo){
        try {
            DocenteDto docente = docenteService.obtenerDocentePorCodigoCurso(cursoCodigo);
            if(docente == null) return null;
            NotaDto nota = notaService.obtenerNotaPorEstudianteCursoDocente(estudianteCodigo, cursoCodigo, docente.codigo());
            return nota != null ? nota.calificacion() : null;
        } catch (Exception ex){
            log.warn("No se pudo obtener nota para estudiante={} curso={}", estudianteCodigo, cursoCodigo, ex);
            return null;
        }
    }

    public boolean estaInscrito(Long cursoCodigo){
        if(cursosInscritos == null) return false;
        return cursosInscritos.stream().anyMatch(r -> r.getCodigo().equals(cursoCodigo));
    }

    public void inscribir(Long cursoCodigo){
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            estudianteService.inscribirEnCurso(authSession.getEstudianteCodigo(), cursoCodigo);
            cargarDatos();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Inscripción", "Inscrito al curso."));
        } catch (Exception ex){
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo inscribir."));
            log.error("Error inscribiendo cursoCodigo={} estudiante={}", cursoCodigo, authSession.getEstudianteCodigo(), ex);
        }
    }

    public void baja(Long cursoCodigo){
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            estudianteService.darseDeBajaCurso(authSession.getEstudianteCodigo(), cursoCodigo);
            cargarDatos();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Baja", "Se dio de baja del curso."));
        } catch (Exception ex){
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo dar de baja."));
            log.error("Error baja cursoCodigo={} estudiante={}", cursoCodigo, authSession.getEstudianteCodigo(), ex);
        }
    }

    public String styleNota(Integer nota){
        if(nota == null) return "color:#9ca3af !important;font-weight:bold;";
        if(nota < 60) return "color:#f87171 !important;font-weight:bold;";
        if(nota < 80) return "color:#fbbf24 !important;font-weight:bold;";
        if(nota < 95) return "color:#34d399 !important;font-weight:bold;";
        return "color:#10b981 !important;font-weight:bold;";
    }
}
