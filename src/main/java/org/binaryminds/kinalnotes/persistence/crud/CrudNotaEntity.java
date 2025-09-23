package org.binaryminds.kinalnotes.persistence.crud;

import org.binaryminds.kinalnotes.persistence.entity.DocenteEntity;
import org.binaryminds.kinalnotes.persistence.entity.NotaEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CrudNotaEntity extends CrudRepository<NotaEntity, Long> {

}
