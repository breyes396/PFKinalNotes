package org.binaryminds.kinalnotes.web.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.binaryminds.kinalnotes.dominio.dto.EstudianteDto;
import org.binaryminds.kinalnotes.dominio.dto.ModNotaDto;
import org.binaryminds.kinalnotes.dominio.dto.NotaDto;
import org.binaryminds.kinalnotes.dominio.service.DocenteService;
import org.binaryminds.kinalnotes.dominio.service.EstudianteService;
import org.binaryminds.kinalnotes.dominio.service.NotaService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@ViewScoped
@Getter
@Setter
public class DocenteAdminView implements Serializable {
    private static final long serialVersionUID = 1L;

    @Data
    public static class EstudianteNotaRow implements Serializable {
        private Long estudianteId;
        private String nombre;
        private String apellido;
        private Integer nota; // null si no hay
        private Long notaCodigo; // para actualizar
        public EstudianteNotaRow(Long estudianteId, String nombre, String apellido){
            this.estudianteId = estudianteId;
            this.nombre = nombre;
            this.apellido = apellido;
        }
    }

    private final AuthSession authSession;
    private final EstudianteService estudianteService;
    private final NotaService notaService;
    private final DocenteService docenteService;

    private List<EstudianteNotaRow> filas;
    private EstudianteNotaRow filaSeleccionada;
    private Integer nuevaNota;

    public DocenteAdminView(AuthSession authSession, EstudianteService estudianteService, NotaService notaService, DocenteService docenteService) {
        this.authSession = authSession;
        this.estudianteService = estudianteService;
        this.notaService = notaService;
        this.docenteService = docenteService;
    }

    @PostConstruct
    public void init(){
        FacesContext ctx = FacesContext.getCurrentInstance();
        if(!authSession.isLogged() || !authSession.isTeacher() || authSession.getDocenteCursoCodigo()==null){
            try {
                ctx.getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath()+"/pages/login.xhtml");
            } catch (IOException ignored) {}
            return;
        }
        cargarFilas();
    }

    public void cargarFilas(){
        filas = new ArrayList<>();
        Long cursoCodigo = authSession.getDocenteCursoCodigo();
        Long docenteCodigo = authSession.getDocenteCodigo();
        if(cursoCodigo==null || docenteCodigo==null) return;
        List<EstudianteDto> estudiantes = estudianteService.obtenerEstudiantesPorCurso(cursoCodigo);
        for(EstudianteDto e : estudiantes){
            EstudianteNotaRow row = new EstudianteNotaRow(e.codigo(), e.name(), e.lastname());
            NotaDto nota = notaService.obtenerNotaPorEstudianteCursoDocente(e.codigo(), cursoCodigo, docenteCodigo);
            if(nota != null){
                row.setNota(nota.calificacion());
                row.setNotaCodigo(nota.codigo());
            }
            filas.add(row);
        }
    }

    public void prepararAsignacion(EstudianteNotaRow row){
        this.filaSeleccionada = row;
        this.nuevaNota = row.getNota();
    }

    public void guardarNota(){
        FacesContext ctx = FacesContext.getCurrentInstance();
        if(filaSeleccionada==null || nuevaNota==null){
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Validación", "Seleccione un estudiante y una nota"));
            return;
        }
        if(nuevaNota < 0 || nuevaNota > 100){
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Validación", "La nota debe estar entre 0 y 100"));
            return;
        }
        Long cursoCodigo = authSession.getDocenteCursoCodigo();
        Long docenteCodigo = authSession.getDocenteCodigo();
        try {
            if(filaSeleccionada.getNotaCodigo()==null){
                // crear
                NotaDto nueva = new NotaDto(null, LocalDate.now(), nuevaNota, filaSeleccionada.getEstudianteId(), cursoCodigo, docenteCodigo);
                NotaDto guardada = notaService.guardarNota(nueva);
                filaSeleccionada.setNota(guardada.calificacion());
                filaSeleccionada.setNotaCodigo(guardada.codigo());
            } else {
                // actualizar
                notaService.actualizarNota(filaSeleccionada.getNotaCodigo(), new ModNotaDto(nuevaNota, filaSeleccionada.getEstudianteId()));
                filaSeleccionada.setNota(nuevaNota);
            }
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Nota", "Nota guardada"));
        } catch (Exception ex){
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo guardar la nota"));
        }
    }

    public String styleNota(Integer nota){
        if(nota == null) return "color:#aaa;";
        if(nota < 60) return "color:#f87171;font-weight:bold;";
        if(nota == 100) return "color:#10b981;font-weight:bold;";
        return "color:#fff;";
    }
}
