package org.binaryminds.kinalnotes.persistence.crud;

import org.binaryminds.kinalnotes.persistence.entity.EstudianteEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CrudEstudianteEntity extends CrudRepository<CrudEstudianteEntity,Long> {
    EstudianteEntity findFirstByNombre(String nombre);
    Optional<EstudianteEntity> findByNombre(String nombre);
}
