package org.binaryminds.kinalnotes.web.mapper;

import org.binaryminds.kinalnotes.dominio.ModUsuarioDto;
import org.binaryminds.kinalnotes.dominio.UsuariosDto;
import org.binaryminds.kinalnotes.dominio.dto.ModDocentesDto;
import org.binaryminds.kinalnotes.persistence.entity.DocenteEntity;
import org.binaryminds.kinalnotes.persistence.entity.UsuarioEntity;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Optional;

public interface DocentesMapper {
    @Mapping(source = "nombre",target = "name")
    @Mapping(source = "asignatura",target = "subject")


    public UsuariosDto toDto(UsuarioEntity Entity);

    UsuariosDto toDto(Optional<UsuarioEntity> Entity);

    public List<UsuariosDto> toDto(Iterable<UsuarioEntity> Entities);

    // auto actualizar el ModUsuarioDto a Usuario entity
    @Mapping(source = "name" ,target = "nombre")
    @Mapping(source = "subject",target = "asignatura")
    void modificarEntityFromDto(ModDocentesDto, @MappingTarget DocenteEntity docenteEntity);

}

