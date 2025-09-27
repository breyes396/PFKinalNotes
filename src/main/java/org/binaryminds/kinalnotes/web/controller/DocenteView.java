package org.binaryminds.kinalnotes.web.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.binaryminds.kinalnotes.dominio.dto.CursoDto;
import org.binaryminds.kinalnotes.dominio.dto.DocenteDto;
import org.binaryminds.kinalnotes.dominio.dto.ModDocenteDto;
import org.binaryminds.kinalnotes.dominio.service.CursoService;
import org.binaryminds.kinalnotes.dominio.service.DocenteService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Named("docenteView")
@ViewScoped
public class DocenteView implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private DocenteService docenteService;
    @Inject
    private CursoService cursoService;

    private List<DocenteDto> docentes;
    private List<CursoDto> cursosDisponibles;

    // Estado para crear/editar
    private DocenteDto nuevoDocente; // si null => creando, si no => editando
    private String nuevoDocenteName;
    private Long nuevoDocenteCodigoCurso;
    private Long nuevoDocenteCodigoUsuario; // TODO: exponer selección de usuario si se requiere

    @PostConstruct
    public void init() {
        recargarListas();
        prepararNuevoDocente();
    }

    private void recargarListas() {
        try {
            this.docentes = new ArrayList<>(docenteService.obtenerTodo());
        } catch (Exception e) {
            this.docentes = new ArrayList<>();
            addError("Error cargando docentes: " + mensajeCorto(e));
        }
        try {
            this.cursosDisponibles = new ArrayList<>(cursoService.obtenerTodo());
        } catch (Exception e) {
            this.cursosDisponibles = new ArrayList<>();
            addError("Error cargando cursos: " + mensajeCorto(e));
        }
    }

    public void prepararNuevoDocente() {
        this.nuevoDocente = null;
        this.nuevoDocenteName = null;
        this.nuevoDocenteCodigoCurso = null;
        this.nuevoDocenteCodigoUsuario = null; // se podría setear automáticamente si hay usuario autenticado
    }

    public void prepararEdicion(DocenteDto row) {
        if (row == null) return;
        this.nuevoDocente = row;
        this.nuevoDocenteName = row.name();
        this.nuevoDocenteCodigoCurso = row.codigo_curso();
        // El usuario original para mantener al actualizar
        this.nuevoDocenteCodigoUsuario = row.codigo_usuario();
    }

    public void guardarDocente() {
        try {
            if (nuevoDocenteName == null || nuevoDocenteName.isBlank()) {
                addWarn("El nombre es obligatorio");
                return;
            }
            if (nuevoDocenteCodigoCurso == null) {
                addWarn("Seleccione un curso");
                return;
            }
            // Si no se provee usuario, intentar reutilizar en edición o asignar placeholder
            if (nuevoDocenteCodigoUsuario == null) {
                if (nuevoDocente != null) {
                    nuevoDocenteCodigoUsuario = nuevoDocente.codigo_usuario();
                }
                if (nuevoDocenteCodigoUsuario == null) {
                    // Placeholder: en una implementación real esto debería venir de la selección de usuario
                    nuevoDocenteCodigoUsuario = 1L; // ADVERTENCIA: puede colisionar si ya existe
                }
            }

            if (nuevoDocente == null) { // crear
                DocenteDto creado = docenteService.guardarDocente(new DocenteDto(null, nuevoDocenteName, nuevoDocenteCodigoCurso, nuevoDocenteCodigoUsuario));
                addInfo("Docente creado: " + creado.name());
            } else { // actualizar
                DocenteDto actualizado = docenteService.actualizarDocente(nuevoDocente.codigo(), new ModDocenteDto(nuevoDocenteName, nuevoDocenteCodigoCurso));
                addInfo("Docente actualizado: " + actualizado.name());
            }
            recargarListas();
            prepararNuevoDocente();
        } catch (Exception e) {
            addError("No se pudo guardar: " + mensajeCorto(e));
        }
    }

    public void eliminarDocente(DocenteDto row) {
        if (row == null) return;
        try {
            docenteService.eliminarDocente(row.codigo());
            addInfo("Docente eliminado: " + row.name());
            recargarListas();
        } catch (Exception e) {
            addError("No se pudo eliminar: " + mensajeCorto(e));
        }
    }

    // Métodos de soporte para la vista
    public String getCursoNombre(Long codigoCurso) {
        if (codigoCurso == null || cursosDisponibles == null) return "";
        Optional<CursoDto> c = cursosDisponibles.stream().filter(k -> Objects.equals(k.codigo(), codigoCurso)).findFirst();
        return c.map(CursoDto::name).orElse("Desconocido");
    }

    public Long getCursoCodigo(CursoDto curso) {
        return curso == null ? null : curso.codigo();
    }

    public String getCursoNombre(CursoDto curso) {
        return curso == null ? "" : curso.name();
    }

    // Getters requeridos por la página
    public List<DocenteDto> getDocentes() { return docentes; }
    public List<CursoDto> getCursosDisponibles() { return cursosDisponibles; }
    public DocenteDto getNuevoDocente() { return nuevoDocente; }
    public String getNuevoDocenteName() { return nuevoDocenteName; }
    public void setNuevoDocenteName(String nuevoDocenteName) { this.nuevoDocenteName = nuevoDocenteName; }
    public Long getNuevoDocenteCodigoCurso() { return nuevoDocenteCodigoCurso; }
    public void setNuevoDocenteCodigoCurso(Long nuevoDocenteCodigoCurso) { this.nuevoDocenteCodigoCurso = nuevoDocenteCodigoCurso; }
    public Long getNuevoDocenteCodigoUsuario() { return nuevoDocenteCodigoUsuario; }
    public void setNuevoDocenteCodigoUsuario(Long nuevoDocenteCodigoUsuario) { this.nuevoDocenteCodigoUsuario = nuevoDocenteCodigoUsuario; }

    // Utilidades de mensajes
    private void addInfo(String msg) { FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null)); }
    private void addWarn(String msg) { FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msg, null)); }
    private void addError(String msg) { FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null)); }

    private String mensajeCorto(Exception e){
        String m = e.getMessage();
        if(m == null) return e.getClass().getSimpleName();
        return m.length() > 120 ? m.substring(0,117)+"..." : m;
    }
}

