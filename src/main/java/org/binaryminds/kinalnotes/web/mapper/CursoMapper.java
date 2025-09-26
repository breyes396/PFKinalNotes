package org.binaryminds.kinalnotes.web.mapper;

import org.binaryminds.kinalnotes.dominio.dto.CursoDto;
import org.binaryminds.kinalnotes.dominio.dto.ModCursoDto;
import org.binaryminds.kinalnotes.persistence.entity.CursoEntity;
import org.binaryminds.kinalnotes.persistence.entity.EstudianteEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CursoMapper {
        @Mapping(source = "nombre", target = "name")
        @Mapping(source = "grado", target = "degree")
        @Mapping(source = "estudiantes", target = "students")
        CursoDto toDto(CursoEntity entity);
        List<CursoDto> toDto(Iterable<CursoEntity> entities);

        @InheritInverseConfiguration
        CursoEntity toEntity(CursoDto cursoDto);

        @Mapping(source = "name", target = "nombre")
        void modificarEntityFromDto(ModCursoDto modCursoDto, @MappingTarget CursoEntity cursoEntity);

        // Helpers para mapear entre EstudianteEntity y su id
        default Long map(EstudianteEntity estudiante) {
            return estudiante != null ? estudiante.getCodigo() : null;
        }

        default EstudianteEntity map(Long codigoEstudiante){
            if (codigoEstudiante == null) return null;
            EstudianteEntity e = new EstudianteEntity();
            e.setCodigo(codigoEstudiante);
            return e;
        }
}
