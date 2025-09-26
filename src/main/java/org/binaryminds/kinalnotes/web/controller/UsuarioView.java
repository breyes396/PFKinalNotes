package org.binaryminds.kinalnotes.web.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.inject.Inject;
import org.binaryminds.kinalnotes.dominio.dto.UsuarioDto;
import org.binaryminds.kinalnotes.dominio.dto.ModUsuarioDto;
import org.binaryminds.kinalnotes.dominio.service.UsuarioService;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class UsuarioView implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioService usuarioService;

    private List<UsuarioDto> usuarios;
    private UsuarioDto usuarioSeleccionado;
    private Long codigoEdicion;
    private UsuarioDto nuevoUsuario;

    @PostConstruct
    public void init() {
        cargarUsuarios();
        prepararNuevoUsuario();
    }

    public void cargarUsuarios() {
        usuarios = usuarioService.obtenerTodo();
    }

    public void prepararNuevoUsuario() {
        nuevoUsuario = new UsuarioDto(null, "", "", org.binaryminds.kinalnotes.dominio.Role.STUDENT);
        codigoEdicion = null;
    }

    public void prepararEdicion(UsuarioDto usuario) {
        nuevoUsuario = new UsuarioDto(
            usuario.codigo(),
            usuario.mail(),
            usuario.password(),
            usuario.role()
        );
        codigoEdicion = usuario.codigo();
    }

    public void guardarUsuario() {
        if (codigoEdicion == null) {
            usuarioService.guardarUsuario(nuevoUsuario);
        } else {
            ModUsuarioDto mod = new ModUsuarioDto(
                nuevoUsuario.mail(),
                nuevoUsuario.password()
            );
            usuarioService.actualizarUsuario(codigoEdicion, mod);
        }
        cargarUsuarios();
    }

    public void eliminarUsuario(UsuarioDto usuario) {
        usuarioService.eliminarUsuario(usuario.codigo());
        cargarUsuarios();
    }

    // Métodos proxy para JSF/PrimeFaces
    public String getNuevoUsuarioCorreo() {
        return nuevoUsuario != null ? nuevoUsuario.mail() : "";
    }
    public void setNuevoUsuarioCorreo(String correo) {
        if (nuevoUsuario != null) {
            nuevoUsuario = new UsuarioDto(
                nuevoUsuario.codigo(),
                correo,
                nuevoUsuario.password(),
                nuevoUsuario.role()
            );
        }
    }
    public String getNuevoUsuarioContrasena() {
        return nuevoUsuario != null ? nuevoUsuario.password() : "";
    }
    public void setNuevoUsuarioContrasena(String contrasena) {
        if (nuevoUsuario != null) {
            nuevoUsuario = new UsuarioDto(
                nuevoUsuario.codigo(),
                nuevoUsuario.mail(),
                contrasena,
                nuevoUsuario.role()
            );
        }
    }
    public String getNuevoUsuarioRol() {
        return nuevoUsuario != null ? nuevoUsuario.role().name() : "";
    }
    public void setNuevoUsuarioRol(String rol) {
        if (nuevoUsuario != null) {
            nuevoUsuario = new UsuarioDto(
                nuevoUsuario.codigo(),
                nuevoUsuario.mail(),
                nuevoUsuario.password(),
                org.binaryminds.kinalnotes.dominio.Role.valueOf(rol)
            );
        }
    }
    public Long getCodigo(UsuarioDto usuario) {
        return usuario != null ? usuario.codigo() : null;
    }
    public String getCorreo(UsuarioDto usuario) {
        return usuario != null ? usuario.mail() : "";
    }
    public String getRol(UsuarioDto usuario) {
        return usuario != null ? usuario.role().name() : "";
    }
    public List<UsuarioDto> getUsuarios() {
        return usuarios;
    }
    public UsuarioDto getNuevoUsuario() {
        return nuevoUsuario;
    }
    public void setNuevoUsuario(UsuarioDto nuevoUsuario) {
        this.nuevoUsuario = nuevoUsuario;
    }
}
