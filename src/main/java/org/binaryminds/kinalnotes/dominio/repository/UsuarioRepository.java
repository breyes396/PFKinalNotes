package org.binaryminds.kinalnotes.dominio.repository;

import org.binaryminds.kinalnotes.dominio.dto.ModUsuarioDto;
import org.binaryminds.kinalnotes.dominio.dto.UsuarioDto;

import java.util.List;

public interface UsuarioRepository {
    List<UsuarioDto> obtenerTodo();
    UsuarioDto obtenerUsuarioPorId(Long id);
    UsuarioDto guardarUsuario(UsuarioDto usuarioDto);
    UsuarioDto actualizarUsuario(Long codigo, ModUsuarioDto modUsuarioDto);
    void eliminarUsuario(Long codigo);
    UsuarioDto obtenerUsuarioPorCorreo(String correo);
}
