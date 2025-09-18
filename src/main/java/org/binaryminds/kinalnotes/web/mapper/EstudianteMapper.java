package org.binaryminds.kinalnotes.web.mapper;

import org.binaryminds.kinalnotes.dominio.dto.EstudianteDto;
import org.binaryminds.kinalnotes.dominio.dto.ModEstudianteDto;
import org.binaryminds.kinalnotes.dominio.dto.UsuarioDto;
import org.binaryminds.kinalnotes.persistence.entity.EstudianteEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EstudianteMapper {
    @Mapping(source = "nombre", target = "name")
    @Mapping(source = "apellido", target = "lastname")
    @Mapping(source = "cursos", target = "courses")
    @Mapping(source = "usuario" , target = "codigo_usuario")
    EstudianteDto toDto(EstudianteEntity entity);
    List<EstudianteDto> toDto(Iterable<EstudianteEntity> entities);

    @InheritInverseConfiguration
    EstudianteEntity toEntity(EstudianteDto estudianteDto);

    @Mapping(source = "courses",target = "cursos")
    void modificarEntityFromDto(ModEstudianteDto modEstudianteDto, @MappingTarget EstudianteEntity estudianteEntity);



}
