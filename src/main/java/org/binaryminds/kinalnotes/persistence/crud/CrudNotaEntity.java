package org.binaryminds.kinalnotes.persistence.crud;

import org.binaryminds.kinalnotes.persistence.entity.NotaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CrudNotaEntity extends CrudRepository<NotaEntity, Long> {
    NotaEntity findFirstByCalificacion(Integer calificacion);
    Optional<NotaEntity> findByCalificacion(Integer calificacion);
    Optional<NotaEntity> findByCodigoEstudianteAndCodigoCursoAndCodigoDocente(Long codigoEstudiante, Long codigoCurso, Long codigoDocente);
    List<NotaEntity> findByCodigoEstudiante(Long codigoEstudiante);
}
