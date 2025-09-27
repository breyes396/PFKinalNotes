package org.binaryminds.kinalnotes.persistence.crud;

import org.binaryminds.kinalnotes.persistence.entity.DocenteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CrudDocenteEntity extends CrudRepository<DocenteEntity, Long> {
    DocenteEntity findFirstByNombre(String nombre);
    Optional<DocenteEntity> findByNombre(String nombre);
    Optional<DocenteEntity> findByCodigoUsuario(Long codigoUsuario);
    Optional<DocenteEntity> findByCodigoCurso(Long codigoCurso);
}
