package org.binaryminds.kinalnotes.dominio.service;

import org.binaryminds.kinalnotes.dominio.dto.ModUsuarioDto;
import org.binaryminds.kinalnotes.dominio.dto.UsuarioDto;
import org.binaryminds.kinalnotes.dominio.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioDto> obtenerTodo(){
        return this.usuarioRepository.obtenerTodo();
    }

    public UsuarioDto obtenerUsuarioPorId(Long codigo){
        return this.usuarioRepository.obtenerUsuarioPorId(codigo);
    }

    public UsuarioDto guardarUsuario(UsuarioDto usuarioDto){
        return this.usuarioRepository.guardarUsuario(usuarioDto);
    }
    public UsuarioDto actualizarUsuario(Long codigo, ModUsuarioDto modUsuarioDto){
        return this.usuarioRepository.actualizarUsuario(codigo, modUsuarioDto);
    }
    public void eliminarUsuario(Long codigo){
       this.usuarioRepository.eliminarUsuario(codigo);
    }

    public UsuarioDto obtenerUsuarioPorCorreo(String correo) {
        return this.usuarioRepository.obtenerUsuarioPorCorreo(correo);
    }
}
