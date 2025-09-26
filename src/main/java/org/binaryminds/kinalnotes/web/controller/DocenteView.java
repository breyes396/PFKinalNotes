package org.binaryminds.kinalnotes.web.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.inject.Inject;
import org.binaryminds.kinalnotes.dominio.dto.DocenteDto;
import org.binaryminds.kinalnotes.dominio.dto.ModDocenteDto;
import org.binaryminds.kinalnotes.dominio.dto.CursoDto;
import org.binaryminds.kinalnotes.dominio.service.DocenteService;
import org.binaryminds.kinalnotes.dominio.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class DocenteView implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private DocenteService docenteService;
    @Inject
    private CursoService cursoService;

    private List<DocenteDto> docentes;
    private List<CursoDto> cursosDisponibles;
    private DocenteDto docenteSeleccionado;
    private Long codigoEdicion;
    private DocenteDto nuevoDocente;

    @PostConstruct
    public void init() {
        cargarDocentes();
        cargarCursosDisponibles();
        prepararNuevoDocente();
    }

    public void cargarDocentes() {
        docentes = docenteService.obtenerTodo();
    }

    public void cargarCursosDisponibles() {
        cursosDisponibles = cursoService.obtenerTodo();
    }

    public void prepararNuevoDocente() {
        cargarCursosDisponibles();
        nuevoDocente = new DocenteDto(null, "", null, null);
        codigoEdicion = null;
    }

    public void prepararEdicion(DocenteDto docente) {
        cargarCursosDisponibles();
        nuevoDocente = new DocenteDto(
            docente.codigo(),
            docente.name(),
            docente.codigo_curso(),
            docente.codigo_usuario()
        );
        codigoEdicion = docente.codigo();
    }

    public void guardarDocente() {
        if (codigoEdicion == null) {
            docenteService.guardarDocente(nuevoDocente);
        } else {
            ModDocenteDto mod = new ModDocenteDto(
                nuevoDocente.name(),
                nuevoDocente.codigo_curso()
            );
            docenteService.actualizarDocente(codigoEdicion, mod);
        }
        cargarDocentes();
    }

    public void eliminarDocente(DocenteDto docente) {
        docenteService.eliminarDocente(docente.codigo());
        cargarDocentes();
    }

    // Métodos proxy para JSF/PrimeFaces
    public String getNuevoDocenteName() {
        return nuevoDocente != null ? nuevoDocente.name() : "";
    }
    public void setNuevoDocenteName(String name) {
        if (nuevoDocente != null) {
            nuevoDocente = new DocenteDto(
                nuevoDocente.codigo(),
                name,
                nuevoDocente.codigo_curso(),
                nuevoDocente.codigo_usuario()
            );
        }
    }
    public Long getNuevoDocenteCodigoCurso() {
        return nuevoDocente != null ? nuevoDocente.codigo_curso() : null;
    }
    public void setNuevoDocenteCodigoCurso(Long codigoCurso) {
        if (nuevoDocente != null) {
            nuevoDocente = new DocenteDto(
                nuevoDocente.codigo(),
                nuevoDocente.name(),
                codigoCurso,
                nuevoDocente.codigo_usuario()
            );
        }
    }
    public Long getNuevoDocenteCodigoUsuario() {
        return nuevoDocente != null ? nuevoDocente.codigo_usuario() : null;
    }
    public void setNuevoDocenteCodigoUsuario(Long codigoUsuario) {
        if (nuevoDocente != null) {
            nuevoDocente = new DocenteDto(
                nuevoDocente.codigo(),
                nuevoDocente.name(),
                nuevoDocente.codigo_curso(),
                codigoUsuario
            );
        }
    }
    public Long getCodigo(DocenteDto docente) {
        return docente != null ? docente.codigo() : null;
    }
    public String getName(DocenteDto docente) {
        return docente != null ? docente.name() : "";
    }
    public Long getCodigoCurso(DocenteDto docente) {
        return docente != null ? docente.codigo_curso() : null;
    }
    public Long getCodigoUsuario(DocenteDto docente) {
        return docente != null ? docente.codigo_usuario() : null;
    }
    public String getCursoNombre(Object curso) {
        if (curso == null) return "";
        if (curso instanceof CursoDto dto) {
            return dto.name();
        }
        try {
            return (String) curso.getClass().getMethod("name").invoke(curso);
        } catch (Exception e) {
            return "";
        }
    }
    public Long getCursoCodigo(Object curso) {
        if (curso == null) return null;
        if (curso instanceof CursoDto dto) {
            return dto.codigo();
        }
        try {
            return (Long) curso.getClass().getMethod("codigo").invoke(curso);
        } catch (Exception e) {
            return null;
        }
    }
    public List<DocenteDto> getDocentes() {
        return docentes;
    }
    public List<CursoDto> getCursosDisponibles() {
        return cursosDisponibles;
    }
    public DocenteDto getNuevoDocente() {
        return nuevoDocente;
    }
    public void setNuevoDocente(DocenteDto nuevoDocente) {
        this.nuevoDocente = nuevoDocente;
    }
}
