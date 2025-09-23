package org.binaryminds.kinalnotes.web.mapper;

import org.binaryminds.kinalnotes.dominio.dto.EstudianteDto;
import org.binaryminds.kinalnotes.dominio.dto.ModEstudianteDto;
import org.binaryminds.kinalnotes.persistence.entity.CursoEntity;
import org.binaryminds.kinalnotes.persistence.entity.EstudianteEntity;
import org.binaryminds.kinalnotes.persistence.entity.UsuarioEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EstudianteMapper {
    @Mapping(source = "nombre", target = "name")
    @Mapping(source = "apellido", target = "lastname")
    @Mapping(source = "cursos", target = "courses")
    @Mapping(source = "usuario" , target = "codigo_usuario", qualifiedByName = "usuarioToId")
    EstudianteDto toDto(EstudianteEntity entity);
    List<EstudianteDto> toDto(Iterable<EstudianteEntity> entities);

    @Mapping(source = "name", target = "nombre")
    @Mapping(source = "lastname", target = "apellido")
    @Mapping(source = "courses",target = "cursos")
    @Mapping(source = "codigo_usuario", target = "usuario", qualifiedByName = "usuarioFromId")
    EstudianteEntity toEntity(EstudianteDto estudianteDto);

    @Mapping(source = "name", target = "nombre")
    @Mapping(source = "lastname", target = "apellido")
    @Mapping(source = "courses",target = "cursos")
    void modificarEntityFromDto(ModEstudianteDto modEstudianteDto, @MappingTarget EstudianteEntity estudianteEntity);

    @Named("usuarioToId")
    default Long usuarioToId(UsuarioEntity usuario) {
        return usuario != null ? usuario.getCodigo() : null;
    }

    @Named("usuarioFromId")
    default UsuarioEntity usuarioFromId(Long codigoUsuario) {
        if (codigoUsuario == null) return null;
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setCodigo(codigoUsuario);
        return usuario;
    }

    // Element mapping para cursos
    default Long cursoToId(CursoEntity curso) {
        return curso != null ? curso.getCodigo() : null;
    }

    default CursoEntity cursoFromId(Long codigo_curso){
        if (codigo_curso == null) return null;
        CursoEntity curso = new CursoEntity();
        curso.setCodigo(codigo_curso);
        return curso;
    }
}
