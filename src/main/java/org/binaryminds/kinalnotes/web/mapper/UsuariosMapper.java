package org.binaryminds.kinalnotes.web.mapper;

import org.binaryminds.kinalnotes.dominio.ModUsuarioDto;
import org.binaryminds.kinalnotes.dominio.UsuariosDto;
import org.binaryminds.kinalnotes.persistence.entity.UsuarioEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Optional;

public interface UsuariosMapper {
    @Mapping(source = "nombre",target = "name")
    @Mapping(source = "correo",target = "mail")
    @Mapping(source = "contraseña",target = "password")
    @Mapping(source = "rol",target = "role")

    public UsuariosDto toDto(UsuarioEntity  Entity);

    UsuariosDto toDto(Optional<UsuarioEntity> Entity);

    public List<UsuariosDto> toDto(Iterable<UsuarioEntity> Entities);

    // auto actualizar el ModUsuarioDto a Usuario entity
    @Mapping(source = "name" ,target = "nombre")
    @Mapping(source = "mail",target = "correo")
   @Mapping(source = "password",target = "contraseña")
    @Mapping(source = "role",target = "rol")
    void modificarEntityFromDto(ModUsuarioDto, @MappingTarget UsuarioEntity peliculaEntity);

}

