package org.binaryminds.kinalnotes.dominio.repository;

import org.binaryminds.kinalnotes.dominio.dto.ModUsuarioDto;
import org.binaryminds.kinalnotes.dominio.dto.UsuariosDto;

import java.util.List;

public interface UsuariosRepository {
    List<UsuariosDto> obtenerTodo();
    UsuariosDto obtenerUsuarioPorId(Long id);
    UsuariosDto guardarUsuario(UsuariosDto usuariosDto);
    UsuariosDto actualizarUsuario(Long codigo, ModUsuarioDto modUsuarioDto);
    void eliminarUsuario(Long codigo);
}
