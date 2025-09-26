package org.binaryminds.kinalnotes.web.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.inject.Inject;
import org.binaryminds.kinalnotes.dominio.dto.NotaDto;
import org.binaryminds.kinalnotes.dominio.dto.ModNotaDto;
import org.binaryminds.kinalnotes.dominio.service.NotaService;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Named
@ViewScoped
public class NotaView implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private NotaService notaService;

    private List<NotaDto> notas;
    private NotaDto notaSeleccionada;
    private Long codigoEdicion;
    private NotaDto nuevaNota;

    @PostConstruct
    public void init() {
        cargarNotas();
        prepararNuevaNota();
    }

    public void cargarNotas() {
        notas = notaService.obtenerTodo();
    }

    public void prepararNuevaNota() {
        nuevaNota = new NotaDto(null, java.time.LocalDate.now(), 0, null, null, null);
        codigoEdicion = null;
    }

    public void prepararEdicion(NotaDto nota) {
        nuevaNota = new NotaDto(
            nota.codigo(),
            nota.date(),
            nota.calificacion(),
            nota.codigo_estudiante(),
            nota.codigo_curso(),
            nota.codigo_docente()
        );
        codigoEdicion = nota.codigo();
    }

    public void guardarNota() {
        if (codigoEdicion == null) {
            notaService.guardarNota(nuevaNota);
        } else {
            ModNotaDto mod = new ModNotaDto(
                nuevaNota.calificacion(),
                nuevaNota.codigo_estudiante()
            );
            notaService.actualizarNota(codigoEdicion, mod);
        }
        cargarNotas();
    }

    public void eliminarNota(NotaDto nota) {
        notaService.eliminarNota(nota.codigo());
        cargarNotas();
    }

    // Métodos proxy para JSF/PrimeFaces
    public java.time.LocalDate getNuevaNotaFecha() {
        return nuevaNota != null ? nuevaNota.date() : java.time.LocalDate.now();
    }
    public void setNuevaNotaFecha(java.time.LocalDate fecha) {
        if (nuevaNota != null) {
            nuevaNota = new NotaDto(
                nuevaNota.codigo(),
                fecha,
                nuevaNota.calificacion(),
                nuevaNota.codigo_estudiante(),
                nuevaNota.codigo_curso(),
                nuevaNota.codigo_docente()
            );
        }
    }
    public Integer getNuevaNotaCalificacion() {
        return nuevaNota != null ? nuevaNota.calificacion() : 0;
    }
    public void setNuevaNotaCalificacion(Integer calificacion) {
        if (nuevaNota != null) {
            nuevaNota = new NotaDto(
                nuevaNota.codigo(),
                nuevaNota.date(),
                calificacion,
                nuevaNota.codigo_estudiante(),
                nuevaNota.codigo_curso(),
                nuevaNota.codigo_docente()
            );
        }
    }
    public Long getNuevaNotaCodigoCurso() {
        return nuevaNota != null ? nuevaNota.codigo_curso() : null;
    }
    public void setNuevaNotaCodigoCurso(Long codigoCurso) {
        if (nuevaNota != null) {
            nuevaNota = new NotaDto(
                nuevaNota.codigo(),
                nuevaNota.date(),
                nuevaNota.calificacion(),
                nuevaNota.codigo_estudiante(),
                codigoCurso,
                nuevaNota.codigo_docente()
            );
        }
    }
    public Long getNuevaNotaCodigoEstudiante() {
        return nuevaNota != null ? nuevaNota.codigo_estudiante() : null;
    }
    public void setNuevaNotaCodigoEstudiante(Long codigoEstudiante) {
        if (nuevaNota != null) {
            nuevaNota = new NotaDto(
                nuevaNota.codigo(),
                nuevaNota.date(),
                nuevaNota.calificacion(),
                codigoEstudiante,
                nuevaNota.codigo_curso(),
                nuevaNota.codigo_docente()
            );
        }
    }
    public Long getNuevaNotaCodigoDocente() {
        return nuevaNota != null ? nuevaNota.codigo_docente() : null;
    }
    public void setNuevaNotaCodigoDocente(Long codigoDocente) {
        if (nuevaNota != null) {
            nuevaNota = new NotaDto(
                nuevaNota.codigo(),
                nuevaNota.date(),
                nuevaNota.calificacion(),
                nuevaNota.codigo_estudiante(),
                nuevaNota.codigo_curso(),
                codigoDocente
            );
        }
    }
    public Long getCodigo(NotaDto nota) {
        return nota != null ? nota.codigo() : null;
    }
    public java.time.LocalDate getFecha(NotaDto nota) {
        return nota != null ? nota.date() : java.time.LocalDate.now();
    }
    public Integer getCalificacion(NotaDto nota) {
        return nota != null ? nota.calificacion() : 0;
    }
    public Long getCodigoCurso(NotaDto nota) {
        return nota != null ? nota.codigo_curso() : null;
    }
    public Long getCodigoEstudiante(NotaDto nota) {
        return nota != null ? nota.codigo_estudiante() : null;
    }
    public Long getCodigoDocente(NotaDto nota) {
        return nota != null ? nota.codigo_docente() : null;
    }
    public List<NotaDto> getNotas() {
        return notas;
    }
    public NotaDto getNuevaNota() {
        return nuevaNota;
    }
    public void setNuevaNota(NotaDto nuevaNota) {
        this.nuevaNota = nuevaNota;
    }
}
