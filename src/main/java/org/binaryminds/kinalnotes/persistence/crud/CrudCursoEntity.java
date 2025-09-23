package org.binaryminds.kinalnotes.persistence.crud;

import org.binaryminds.kinalnotes.persistence.entity.CursoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CrudCursoEntity  extends CrudRepository<CursoEntity , Long> {
    CursoEntity findFirstByNombre(String nombre);
    Optional<CursoEntity> findByNombre(String nombre);
}
