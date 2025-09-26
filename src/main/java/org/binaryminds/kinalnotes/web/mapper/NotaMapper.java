package org.binaryminds.kinalnotes.web.mapper;

import org.binaryminds.kinalnotes.dominio.dto.ModNotaDto;
import org.binaryminds.kinalnotes.dominio.dto.NotaDto;
import org.binaryminds.kinalnotes.persistence.entity.NotaEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotaMapper {
    @Mapping(source = "fecha", target = "date")
    @Mapping(source = "codigoCurso", target = "codigo_curso")
    @Mapping(source = "codigoEstudiante", target = "codigo_estudiante")
    @Mapping(source = "codigoDocente", target = "codigo_docente")
    NotaDto toDto(NotaEntity entity);
    List<NotaDto> toDto(Iterable<NotaEntity> entities);

    @InheritInverseConfiguration
    NotaEntity toEntity(NotaDto notaDto);

    @Mapping(source = "calificacion", target = "calificacion")
    @Mapping(source = "codigo_estudiante", target = "codigoEstudiante")
    void modificarEntityFromDto(ModNotaDto modNotaDto, @MappingTarget NotaEntity notaEntity);
}
