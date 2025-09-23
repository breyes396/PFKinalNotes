package org.binaryminds.kinalnotes.persistence.crud;

import org.binaryminds.kinalnotes.persistence.entity.CursoEntity;
import org.binaryminds.kinalnotes.persistence.entity.DocenteEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CrudCursoEntity  extends CrudRepository<CursoEntity , Long> {
    CursoEntity findFirstByNombre(String nombre);
    Optional<CursoEntity> findByNombre(String nombre);
}
