package org.binaryminds.kinalnotes.persistence;

import org.binaryminds.kinalnotes.dominio.dto.ModUsuarioDto;
import org.binaryminds.kinalnotes.dominio.dto.UsuariosDto;
import org.binaryminds.kinalnotes.dominio.repository.UsuariosRepository;
import org.binaryminds.kinalnotes.persistence.crud.CrudUsuariosEntity;
import org.binaryminds.kinalnotes.persistence.entity.UsuarioEntity;
import org.binaryminds.kinalnotes.web.mapper.UsuarioMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsuarioEntityRepository implements UsuariosRepository {

    private final CrudUsuariosEntity crudUsuarios;
    private final UsuarioMapper usuarioMapper;

    public UsuarioEntityRepository(CrudUsuariosEntity crudUsuarios, UsuarioMapper usuarioMapper) {
        this.crudUsuarios = crudUsuarios;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public List<UsuariosDto> obtenerTodo() {
        return this.usuarioMapper.toDto(this.crudUsuarios.findAll());
    }

    @Override
    public UsuariosDto obtenerUsuarioPorId(Long codigo) {
        if(codigo == null){
            throw new IllegalArgumentException("El codigo no puede ser nulo");
        } else {
            return this.usuarioMapper.toDto(this.crudUsuarios.findById(codigo).orElse(null));
        }
    }

    @Override
    public UsuariosDto guardarUsuario(UsuariosDto usuariosDto) {
        if (this.crudUsuarios.findByname(usuariosDto.name())!=null){
            throw new IllegalArgumentException("El usuario no puede ser registrado");
        }
        UsuarioEntity usuario = this.usuarioMapper.toEntity(usuariosDto);
        this.crudUsuarios.save(usuario);
        return this.usuarioMapper.toDto(usuario);
    }

    @Override
    public UsuariosDto actualizarUsuario(Long codigo, ModUsuarioDto modUsuarioDto) {
        UsuarioEntity usuario = this.crudUsuarios.findById(codigo).orElse(null);
        this.usuarioMapper.modificarEntityFromDto(modUsuarioDto, usuario);
        return this.usuarioMapper.toDto(this.crudUsuarios.save(usuario));
    }

    @Override
    public void eliminarUsuario(Long codigo) {
        UsuarioEntity usuarioEntity = this.crudUsuarios.findById(codigo).orElse(null);
        if(usuarioEntity==null){
            throw new IllegalArgumentException("El usuario no puede ser eliminado");
        } else {
            this.crudUsuarios.deleteById(codigo);
        }
    }
}
