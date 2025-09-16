package org.binaryminds.kinalnotes.persistence;

import org.binaryminds.kinalnotes.dominio.dto.ModUsuarioDto;
import org.binaryminds.kinalnotes.dominio.dto.UsuarioDto;
import org.binaryminds.kinalnotes.dominio.exception.UsuarioNoExisteException;
import org.binaryminds.kinalnotes.dominio.exception.UsuarioYaExisteException;
import org.binaryminds.kinalnotes.dominio.repository.UsuarioRepository;
import org.binaryminds.kinalnotes.persistence.crud.CrudUsuarioEntity;
import org.binaryminds.kinalnotes.persistence.entity.UsuarioEntity;
import org.binaryminds.kinalnotes.web.mapper.UsuarioMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsuarioEntityRepository implements UsuarioRepository {

    private final CrudUsuarioEntity crudUsuario;
    private final UsuarioMapper usuarioMapper;

    public UsuarioEntityRepository(CrudUsuarioEntity crudUsuario, UsuarioMapper usuarioMapper) {
        this.crudUsuario = crudUsuario;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public List<UsuarioDto> obtenerTodo() {
        return this.usuarioMapper.toDto(this.crudUsuario.findAll());
    }

    @Override
    public UsuarioDto obtenerUsuarioPorId(Long codigo) {
        if(codigo == null){
            throw new UsuarioNoExisteException(codigo);
        } else {
            return this.usuarioMapper.toDto(this.crudUsuario.findById(codigo).orElse(null));
        }
    }

    @Override
    public UsuarioDto guardarUsuario(UsuarioDto usuarioDto) {
        if(this.crudUsuario.findFirstByCorreo(usuarioDto.mail())!=null){
            throw new UsuarioYaExisteException(usuarioDto.mail());
        }
        UsuarioEntity usuario = this.usuarioMapper.toEntity(usuarioDto);
        usuario.setRol("STUDENT");
        this.crudUsuario.save(usuario);
        return this.usuarioMapper.toDto(usuario);
    }

    @Override
    public UsuarioDto actualizarUsuario(Long codigo, ModUsuarioDto modUsuarioDto) {
        UsuarioEntity usuario = this.crudUsuario.findById(codigo).orElse(null);
        this.usuarioMapper.modificarEntityFromDto(modUsuarioDto, usuario);
        return this.usuarioMapper.toDto(this.crudUsuario.save(usuario));
    }

    @Override
    public void eliminarUsuario(Long codigo) {
        UsuarioEntity usuarioEntity = this.crudUsuario.findById(codigo).orElse(null);
        if(usuarioEntity==null){
            throw new UsuarioNoExisteException(codigo);
        } else {
            this.crudUsuario.deleteById(codigo);
        }
    }

    @Override
    public UsuarioDto obtenerUsuarioPorCorreo(String correo) {
        if(correo == null){
            throw new IllegalArgumentException("El correo no puede ser nulo");
        } else {
            return this.usuarioMapper.toDto(this.crudUsuario.findByCorreo(correo).orElse(null));
        }
    }
}