package org.binaryminds.kinalnotes.dominio.service;

import org.binaryminds.kinalnotes.dominio.dto.ModUsuarioDto;
import org.binaryminds.kinalnotes.dominio.dto.UsuariosDto;
import org.binaryminds.kinalnotes.dominio.repository.UsuariosRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuariosRepository usuariosRepository;

    public UsuarioService(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    public List<UsuariosDto> obtenerTodo(){
        return this.usuariosRepository.obtenerTodo();
    }

    public UsuariosDto obtenerUsuarioPorId(Long codigo){
        return this.usuariosRepository.obtenerUsuarioPorId(codigo);
    }

    public UsuariosDto guardarUsuario(UsuariosDto usuariosDto){
        return this.usuariosRepository.guardarUsuario(usuariosDto);
    }
    public UsuariosDto actualizarUsuario(Long codigo, ModUsuarioDto modUsuarioDto){
        return this.usuariosRepository.actualizarUsuario(codigo, modUsuarioDto);
    }
    public void eliminarUsuario(Long codigo){
       this.usuariosRepository.eliminarUsuario(codigo);
    }

}
