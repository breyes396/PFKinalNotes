package org.binaryminds.kinalnotes.web.mapper;


import org.binaryminds.kinalnotes.dominio.dto.CursoDto;
import org.binaryminds.kinalnotes.dominio.dto.DocenteDto;
import org.binaryminds.kinalnotes.dominio.dto.ModCursoDto;
import org.binaryminds.kinalnotes.dominio.dto.ModDocenteDto;
import org.binaryminds.kinalnotes.persistence.entity.CursoEntity;
import org.binaryminds.kinalnotes.persistence.entity.DocenteEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;
@Mapper(componentModel = "spring")
public interface CursoMapper {




        @Mapping(source = "nombre", target = "name")
        @Mapping(source = "grado", target = "degree")
        @Mapping(source = "codigoEstudiante", target = "codigo_estudiante")
        CursoDto toDto(CursoEntity entity);
        List<CursoDto> toDto(Iterable<CursoDto> entities);

        @InheritInverseConfiguration
        DocenteEntity toEntity(CursoDto cursoDto);

        @Mapping(source = "name", target = "nombre")
        @Mapping(source = "degree", target = "grado")
        @Mapping(source = "codigo_estudiante", target = "codigoEstudiante")
        void modificarEntityFromDto(ModCursoDto modCursoDto, @MappingTarget CursoEntity cursoEntity);
}
