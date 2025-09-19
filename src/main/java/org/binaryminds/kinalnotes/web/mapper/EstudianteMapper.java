package org.binaryminds.kinalnotes.web.mapper;

import org.binaryminds.kinalnotes.dominio.dto.EstudianteDto;
import org.binaryminds.kinalnotes.dominio.dto.ModEstudianteDto;
import org.binaryminds.kinalnotes.persistence.entity.CursoEntity;
import org.binaryminds.kinalnotes.persistence.entity.EstudianteEntity;
import org.binaryminds.kinalnotes.persistence.entity.UsuarioEntity;
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

    default Long map(UsuarioEntity usuario) {
        return usuario != null ? usuario.getCodigo() : null;
    }

    default UsuarioEntity map(Integer codigoUsuario) {
        if (codigoUsuario == null) return null;
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setCodigo(Long.valueOf(codigoUsuario));
        return usuario;
    }

    default Long map(CursoEntity curso) {
        return curso != null ? curso.getCodigo() : null;
    }

    default CursoEntity map(Long codigo_curso){
        if (codigo_curso == null) return null;
        CursoEntity curso = new CursoEntity();
        curso.setCodigo(codigo_curso);
        return curso;
    }
}
