package org.binaryminds.kinalnotes.web.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
    private java.math.BigDecimal nuevaNota;
    private Long estudianteIdSeleccionado;

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
        log.debug("cargarFilas cursoCodigo={} docenteCodigo={}", cursoCodigo, docenteCodigo);
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
        log.debug("Filas cargadas: {}", filas.size());
    }

    public void prepararAsignacion(EstudianteNotaRow row){
        log.debug("Preparar asignacion para estudianteId={} notaActual={}", row.getEstudianteId(), row.getNota());
        this.filaSeleccionada = row;
        this.estudianteIdSeleccionado = row.getEstudianteId();
        this.nuevaNota = row.getNota() != null ? new java.math.BigDecimal(row.getNota()) : null;
    }

    private void reconstruirFilaSiNecesario(){
        if(filaSeleccionada == null && estudianteIdSeleccionado != null && filas != null){
            for(EstudianteNotaRow r : filas){
                if(estudianteIdSeleccionado.equals(r.getEstudianteId())){
                    filaSeleccionada = r;
                    log.debug("Fila reconstruida para estudianteId={}", estudianteIdSeleccionado);
                    break;
                }
            }
        }
    }

    public void guardarNota(){
        FacesContext ctx = FacesContext.getCurrentInstance();
        reconstruirFilaSiNecesario();
        log.debug("Intentando guardar nota. filaSeleccionada={} nuevaNota={}", filaSeleccionada!=null?filaSeleccionada.getEstudianteId():null, nuevaNota);
        if(filaSeleccionada==null || nuevaNota==null){
            log.warn("Validación fallida: filaSeleccionada o nuevaNota null (estudianteIdSeleccionado={})", estudianteIdSeleccionado);
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Validación", "Seleccione un estudiante y una nota"));
            return;
        }
        int notaInt;
        try {
            notaInt = nuevaNota.intValueExact();
        } catch (ArithmeticException ex){
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Validación", "La nota debe ser un entero"));
            return;
        }
        if(notaInt < 0 || notaInt > 100){
            log.warn("Validación fallida: nota fuera de rango {}", notaInt);
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Validación", "La nota debe estar entre 0 y 100"));
            return;
        }
        Long cursoCodigo = authSession.getDocenteCursoCodigo();
        Long docenteCodigo = authSession.getDocenteCodigo();
        try {
            if(filaSeleccionada.getNotaCodigo()==null){
                log.debug("Creando nueva nota estudianteId={} curso={} docente={} calificacion={}", filaSeleccionada.getEstudianteId(), cursoCodigo, docenteCodigo, notaInt);
                NotaDto nueva = new NotaDto(null, LocalDate.now(), notaInt, filaSeleccionada.getEstudianteId(), cursoCodigo, docenteCodigo);
                NotaDto guardada = notaService.guardarNota(nueva);
                filaSeleccionada.setNota(guardada.calificacion());
                filaSeleccionada.setNotaCodigo(guardada.codigo());
            } else {
                log.debug("Actualizando nota existente codigoNota={} nuevaCalificacion={}", filaSeleccionada.getNotaCodigo(), notaInt);
                notaService.actualizarNota(filaSeleccionada.getNotaCodigo(), new ModNotaDto(notaInt, filaSeleccionada.getEstudianteId()));
                filaSeleccionada.setNota(notaInt);
            }
            cargarFilas();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Nota", "Nota guardada correctamente"));
            log.debug("Nota guardada ok");
            filaSeleccionada = null;
            estudianteIdSeleccionado = null;
            nuevaNota = null;
        } catch (Exception ex){
            log.error("Error guardando nota", ex);
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo guardar la nota"));
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
