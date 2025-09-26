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
    @Mapping(source = "courses",target = "cursos", qualifiedByName = "idsToCursos")
    @Mapping(source = "codigo_usuario", target = "usuario", qualifiedByName = "usuarioFromId")
    EstudianteEntity toEntity(EstudianteDto estudianteDto);

    @Mapping(target = "nombre", source = "name")
    @Mapping(target = "apellido", source = "lastname")
    @Mapping(target = "cursos", source = "courses", qualifiedByName = "idsToCursos")
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

    @Named("idsToCursos")
    default List<CursoEntity> idsToCursos(List<Long> ids) {
        if (ids == null) return List.of();
        return ids.stream().map(this::cursoFromId).toList();
    }

    @Named("cursosToIds")
    default List<Long> cursosToIds(List<CursoEntity> cursos) {
        if (cursos == null) return List.of();
        return cursos.stream().map(this::cursoToId).toList();
    }
}
