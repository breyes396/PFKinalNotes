package org.binaryminds.kinalnotes.persistence.crud;

import org.binaryminds.kinalnotes.persistence.entity.UsuarioEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CrudUsuarioEntity extends CrudRepository<UsuarioEntity, Long> {
    UsuarioEntity findFirstByCorreo(String correo);
    Optional<UsuarioEntity> findByCorreo(String correo);
}
