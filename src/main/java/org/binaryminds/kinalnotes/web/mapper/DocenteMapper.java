package org.binaryminds.kinalnotes.web.mapper;

import org.binaryminds.kinalnotes.dominio.dto.DocenteDto;
import org.binaryminds.kinalnotes.dominio.dto.ModDocenteDto;
import org.binaryminds.kinalnotes.persistence.entity.DocenteEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocenteMapper {

    @Mapping(source = "nombre", target = "name")
    @Mapping(source = "codigoCurso", target = "codigo_curso")
    @Mapping(source = "codigoUsuario", target = "codigo_usuario")
    DocenteDto toDto(DocenteEntity entity);
    List<DocenteDto> toDto(Iterable<DocenteEntity> entities);

    @InheritInverseConfiguration
    DocenteEntity toEntity(DocenteDto docenteDto);

    @Mapping(source = "name", target = "nombre")
    @Mapping(source = "codigo_curso", target = "codigoCurso")
    void modificarEntityFromDto(ModDocenteDto modDocenteDto, @MappingTarget DocenteEntity docenteEntity);
}
