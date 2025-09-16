package org.binaryminds.kinalnotes.web.mapper;

import org.binaryminds.kinalnotes.dominio.dto.ModUsuarioDto;
import org.binaryminds.kinalnotes.dominio.dto.UsuarioDto;
import org.binaryminds.kinalnotes.persistence.entity.UsuarioEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;


@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UsuarioMapper {

    @Mapping(source = "correo", target ="mail" )
    @Mapping(source = "contrasena", target ="password")
    @Mapping(source = "rol", target = "role", qualifiedByName = "generarRole")
    UsuarioDto toDto(UsuarioEntity entity);
    List<UsuarioDto> toDto(Iterable<UsuarioEntity> entities);

    @InheritInverseConfiguration
    @Mapping(source = "role", target = "rol", qualifiedByName = "generarRol")
    UsuarioEntity toEntity(UsuarioDto usuarioDto);

    @Mapping(source = "mail", target = "correo")
    @Mapping(source = "password", target = "contrasena")
    void modificarEntityFromDto(ModUsuarioDto modUsuarioDto, @MappingTarget UsuarioEntity usuarioEntity);
}