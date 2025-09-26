package org.binaryminds.kinalnotes.persistence.crud;

import org.binaryminds.kinalnotes.persistence.entity.EstudianteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CrudEstudianteEntity extends CrudRepository<EstudianteEntity, Long> {
    EstudianteEntity findFirstByNombre(String nombre);
    Optional<EstudianteEntity> findByNombre(String nombre);
    java.util.List<EstudianteEntity> findByCursos_Codigo(Long codigoCurso);
    Optional<EstudianteEntity> findByUsuario_Codigo(Long codigoUsuario);
}
