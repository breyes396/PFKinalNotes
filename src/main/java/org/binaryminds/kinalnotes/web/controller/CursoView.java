package org.binaryminds.kinalnotes.web.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.inject.Inject;
import org.binaryminds.kinalnotes.dominio.dto.CursoDto;
import org.binaryminds.kinalnotes.dominio.dto.ModCursoDto;
import org.binaryminds.kinalnotes.dominio.service.CursoService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class CursoView implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private CursoService cursoService;

    private List<CursoDto> cursos;
    private CursoDto cursoSeleccionado;
    private Long codigoEdicion;
    private CursoDto nuevoCurso;

    @PostConstruct
    public void init() {
        cargarCursos();
        prepararNuevoCurso();
    }

    public void cargarCursos() {
        cursos = cursoService.obtenerTodo();
    }

    public void prepararNuevoCurso() {
        nuevoCurso = new CursoDto(null, "", "", new java.util.ArrayList<>());
        codigoEdicion = null;
    }

    public void prepararEdicion(CursoDto curso) {
        nuevoCurso = new CursoDto(
            curso.codigo(),
            curso.name(),
            curso.degree(),
            curso.students()
        );
        codigoEdicion = curso.codigo();
    }

    public void guardarCurso() {
        if (codigoEdicion == null) {
            cursoService.guardarCurso(nuevoCurso);
        } else {
            ModCursoDto mod = new ModCursoDto(
                nuevoCurso.name(),
                nuevoCurso.degree(),
                nuevoCurso.students()
            );
            cursoService.actualizarCurso(codigoEdicion, mod);
        }
        cargarCursos();
    }

    public void eliminarCurso(CursoDto curso) {
        cursoService.eliminarCurso(curso.codigo());
        cargarCursos();
    }

    // Métodos proxy para JSF/PrimeFaces
    public String getNuevoCursoName() {
        return nuevoCurso != null ? nuevoCurso.name() : "";
    }
    public void setNuevoCursoName(String name) {
        if (nuevoCurso != null) {
            nuevoCurso = new CursoDto(
                nuevoCurso.codigo(),
                name,
                nuevoCurso.degree(),
                nuevoCurso.students()
            );
        }
    }
    public String getNuevoCursoDegree() {
        return nuevoCurso != null ? nuevoCurso.degree() : "";
    }
    public void setNuevoCursoDegree(String degree) {
        if (nuevoCurso != null) {
            nuevoCurso = new CursoDto(
                nuevoCurso.codigo(),
                nuevoCurso.name(),
                degree,
                nuevoCurso.students()
            );
        }
    }
    public java.util.List<Long> getNuevoCursoStudents() {
        return nuevoCurso != null ? nuevoCurso.students() : new java.util.ArrayList<>();
    }
    public void setNuevoCursoStudents(java.util.List<Long> students) {
        if (nuevoCurso != null) {
            nuevoCurso = new CursoDto(
                nuevoCurso.codigo(),
                nuevoCurso.name(),
                nuevoCurso.degree(),
                students
            );
        }
    }
    public Long getCodigo(CursoDto curso) {
        return curso != null ? curso.codigo() : null;
    }
    public String getName(CursoDto curso) {
        return curso != null ? curso.name() : "";
    }
    public String getDegree(CursoDto curso) {
        return curso != null ? curso.degree() : "";
    }
    public java.util.List<Long> getStudents(CursoDto curso) {
        return curso != null ? curso.students() : new java.util.ArrayList<>();
    }
    public List<CursoDto> getCursos() {
        return cursos;
    }
    public CursoDto getNuevoCurso() {
        return nuevoCurso;
    }
    public void setNuevoCurso(CursoDto nuevoCurso) {
        this.nuevoCurso = nuevoCurso;
    }
}
