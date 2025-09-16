package org.binaryminds.kinalnotes.persistence.crud;

import org.binaryminds.kinalnotes.dominio.User;
import org.binaryminds.kinalnotes.persistence.entity.UsuarioEntity;
import org.springframework.data.repository.CrudRepository;

public interface CrudUsuariosEntity extends CrudRepository<UsuarioEntity, Long> {
    UsuarioEntity findByNombre(String nombre);
}
