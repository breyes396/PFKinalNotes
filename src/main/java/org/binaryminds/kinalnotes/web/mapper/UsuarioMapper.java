package org.binaryminds.kinalnotes.web.mapper;


import org.binaryminds.kinalnotes.dominio.dto.ModUsuarioDto;
import org.binaryminds.kinalnotes.dominio.dto.UsuariosDto;
import org.binaryminds.kinalnotes.dominio.service.UsuarioService;
import org.binaryminds.kinalnotes.persistence.entity.UsuarioEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;


@Mapper(componentModel = "spring", uses = {UsuarioMapper.class})
public interface UsuarioMapper {

    @Mapping(source = "nombre" ,target = "name")
    @Mapping(source = "correo", target ="mail" )
    @Mapping(source = "contrasena", target = "password")
    @Mapping(source = "rol", target = "role")

    public UsuariosDto toDto(UsuarioEntity Entity);
    public List<UsuariosDto> toDto(Iterable<UsuarioEntity> entities);

    @InheritInverseConfiguration
    @Mapping(source = "role", target = "rol")
    UsuarioEntity toEntity(UsuariosDto usuariosDto);

    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "correo", target = "correo")
    @Mapping(source = "contrasena", target = "contrasena")
    void modificarEntityFromDto(ModUsuarioDto modUsuariosDto, @MappingTarget UsuarioEntity usuarioEntity);
}







