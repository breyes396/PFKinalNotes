package org.binaryminds.kinalnotes.persistence.crud;

import org.binaryminds.kinalnotes.persistence.entity.NotaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CrudNotaEntity extends CrudRepository<NotaEntity, Long> {
    NotaEntity findFirstByCalificacion(Integer calificacion);
    Optional<NotaEntity> findByCalificacion(Integer calificacion);
}
