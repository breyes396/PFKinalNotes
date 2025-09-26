package org.binaryminds.kinalnotes.web.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.inject.Inject;
import org.binaryminds.kinalnotes.dominio.dto.EstudianteDto;
import org.binaryminds.kinalnotes.dominio.dto.ModEstudianteDto;
import org.binaryminds.kinalnotes.dominio.dto.CursoDto;
import org.binaryminds.kinalnotes.dominio.service.EstudianteService;
import org.binaryminds.kinalnotes.dominio.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class EstudianteView implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private EstudianteService estudianteService;
    @Inject
    private CursoService cursoService;

    private List<EstudianteDto> estudiantes;
    private List<CursoDto> cursosDisponibles;
    private EstudianteDto estudianteSeleccionado;
    private Long codigoEdicion;
    private EstudianteDto nuevoEstudiante;

    @PostConstruct
    public void init() {
        cargarEstudiantes();
        cargarCursosDisponibles();
        prepararNuevoEstudiante();
    }

    public void cargarEstudiantes() {
        estudiantes = estudianteService.obtenerTodo();
    }

    public void cargarCursosDisponibles() {
        cursosDisponibles = cursoService.obtenerTodo();
    }

    public void prepararNuevoEstudiante() {
        cargarCursosDisponibles();
        nuevoEstudiante = new EstudianteDto(null, "", "", new java.util.ArrayList<>(), null);
        codigoEdicion = null;
    }

    public void prepararEdicion(EstudianteDto estudiante) {
        cargarCursosDisponibles();
        nuevoEstudiante = new EstudianteDto(
            estudiante.codigo(),
            estudiante.name(),
            estudiante.lastname(),
            estudiante.courses() != null ? new java.util.ArrayList<>(estudiante.courses()) : new java.util.ArrayList<>(),
            estudiante.codigo_usuario()
        );
        codigoEdicion = estudiante.codigo();
    }

    public void guardarEstudiante() {
        if (codigoEdicion == null) {
            estudianteService.guardarEstudiante(nuevoEstudiante);
        } else {
            ModEstudianteDto mod = new ModEstudianteDto(
                nuevoEstudiante.name(),
                nuevoEstudiante.lastname(),
                nuevoEstudiante.courses()
            );
            estudianteService.actualizarEstudiante(codigoEdicion, mod);
        }
        cargarEstudiantes();
    }

    public void eliminarEstudiante(EstudianteDto estudiante) {
        estudianteService.eliminarEstudiante(estudiante.codigo());
        cargarEstudiantes();
    }

    // Métodos proxy para JSF/PrimeFaces (binding con records)
    public String getNuevoEstudianteName() {
        return nuevoEstudiante != null ? nuevoEstudiante.name() : "";
    }
    public void setNuevoEstudianteName(String name) {
        if (nuevoEstudiante != null) {
            nuevoEstudiante = new EstudianteDto(
                nuevoEstudiante.codigo(),
                name,
                nuevoEstudiante.lastname(),
                nuevoEstudiante.courses(),
                nuevoEstudiante.codigo_usuario()
            );
        }
    }
    public String getNuevoEstudianteLastname() {
        return nuevoEstudiante != null ? nuevoEstudiante.lastname() : "";
    }
    public void setNuevoEstudianteLastname(String lastname) {
        if (nuevoEstudiante != null) {
            nuevoEstudiante = new EstudianteDto(
                nuevoEstudiante.codigo(),
                nuevoEstudiante.name(),
                lastname,
                nuevoEstudiante.courses(),
                nuevoEstudiante.codigo_usuario()
            );
        }
    }
    public java.util.List<Long> getNuevoEstudianteCourses() {
        return nuevoEstudiante != null ? nuevoEstudiante.courses() : new java.util.ArrayList<>();
    }
    public void setNuevoEstudianteCourses(java.util.List<Long> courses) {
        if (nuevoEstudiante != null) {
            nuevoEstudiante = new EstudianteDto(
                nuevoEstudiante.codigo(),
                nuevoEstudiante.name(),
                nuevoEstudiante.lastname(),
                courses,
                nuevoEstudiante.codigo_usuario()
            );
        }
    }
    public Long getNuevoEstudianteCodigoUsuario() {
        return nuevoEstudiante != null ? nuevoEstudiante.codigo_usuario() : null;
    }
    public void setNuevoEstudianteCodigoUsuario(Long codigoUsuario) {
        if (nuevoEstudiante != null) {
            nuevoEstudiante = new EstudianteDto(
                nuevoEstudiante.codigo(),
                nuevoEstudiante.name(),
                nuevoEstudiante.lastname(),
                nuevoEstudiante.courses(),
                codigoUsuario
            );
        }
    }
    public Long getCodigo(EstudianteDto estudiante) {
        return estudiante != null ? estudiante.codigo() : null;
    }
    public String getName(EstudianteDto estudiante) {
        return estudiante != null ? estudiante.name() : "";
    }
    public String getLastname(EstudianteDto estudiante) {
        return estudiante != null ? estudiante.lastname() : "";
    }
    public java.util.List<Long> getCourses(EstudianteDto estudiante) {
        return estudiante != null ? estudiante.courses() : new java.util.ArrayList<>();
    }
    public Long getCodigoUsuario(EstudianteDto estudiante) {
        return estudiante != null ? estudiante.codigo_usuario() : null;
    }
    public String getName(Object estudiante) {
        if (estudiante instanceof EstudianteDto dto) {
            return dto.name();
        }
        return "";
    }
    public String getLastname(Object estudiante) {
        if (estudiante instanceof EstudianteDto dto) {
            return dto.lastname();
        }
        return "";
    }
    public List<Long> getCourses(Object estudiante) {
        if (estudiante instanceof EstudianteDto dto) {
            return dto.courses();
        }
        return new ArrayList<>();
    }
    public String getCursoNombre(Object curso) {
        if (curso == null) return "";
        if (curso instanceof org.binaryminds.kinalnotes.dominio.dto.CursoDto dto) {
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
        if (curso instanceof org.binaryminds.kinalnotes.dominio.dto.CursoDto dto) {
            return dto.codigo();
        }
        try {
            return (Long) curso.getClass().getMethod("codigo").invoke(curso);
        } catch (Exception e) {
            return null;
        }
    }

    // Getters y Setters
    public List<EstudianteDto> getEstudiantes() {
        return estudiantes;
    }
    public List<CursoDto> getCursosDisponibles() {
        return cursosDisponibles;
    }
    public EstudianteDto getNuevoEstudiante() {
        return nuevoEstudiante;
    }
    public void setNuevoEstudiante(EstudianteDto nuevoEstudiante) {
        this.nuevoEstudiante = nuevoEstudiante;
    }
}
