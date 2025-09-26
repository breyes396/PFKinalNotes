package org.binaryminds.kinalnotes.persistence;

import org.binaryminds.kinalnotes.dominio.dto.DocenteDto;
import org.binaryminds.kinalnotes.dominio.dto.ModDocenteDto;
import org.binaryminds.kinalnotes.dominio.exception.DocenteNoExisteException;
import org.binaryminds.kinalnotes.dominio.exception.DocenteYaExisteException;
import org.binaryminds.kinalnotes.dominio.repository.DocenteRepository;
import org.binaryminds.kinalnotes.persistence.crud.CrudDocenteEntity;
import org.binaryminds.kinalnotes.persistence.entity.DocenteEntity;
import org.binaryminds.kinalnotes.web.mapper.DocenteMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DocenteEntityRepository implements DocenteRepository {

    private final CrudDocenteEntity crudDocente;
    private final DocenteMapper docenteMapper;

    public DocenteEntityRepository(CrudDocenteEntity crudDocente, DocenteMapper docenteMapper) {
        this.crudDocente = crudDocente;
        this.docenteMapper = docenteMapper;
    }

    @Override
    public List<DocenteDto> obtenerTodo() {
        return this.docenteMapper.toDto(this.crudDocente.findAll());
    }

    @Override
    public DocenteDto obtenerDocentePorId(Long id) {
        if (id == null) {
            throw new DocenteNoExisteException(id);
        }
        return this.docenteMapper.toDto(this.crudDocente.findById(id).orElse(null));
    }

    @Override
    public DocenteDto guardarDocente(DocenteDto docenteDto) {
        if (this.crudDocente.findFirstByNombre(docenteDto.name()) != null) {
            throw new DocenteYaExisteException(docenteDto.name());
        } else {
            DocenteEntity docente = this.docenteMapper.toEntity(docenteDto);
            this.crudDocente.save(docente);
            return this.docenteMapper.toDto(docente);
        }

    }

    @Override
    public DocenteDto actualizarDocente(Long codigo, ModDocenteDto modDocenteDto) {
        DocenteEntity docente = this.crudDocente.findById(codigo).orElse(null);
        if (docente == null) {
            throw new DocenteNoExisteException(codigo);
        }
        this.docenteMapper.modificarEntityFromDto(modDocenteDto, docente);
        return this.docenteMapper.toDto(this.crudDocente.save(docente));
    }

    @Override
    public void eliminarDocente(Long codigo) {
        DocenteEntity docenteEntity = this.crudDocente.findById(codigo).orElse(null);
        if (docenteEntity == null) {
            throw new DocenteNoExisteException(codigo);
        } else {
            this.crudDocente.deleteById(codigo);
        }
    }

    @Override
    public DocenteDto obtenerDocentePorNombre(String nombre) {
        if (nombre == null) {
            throw new IllegalArgumentException("El nombre no existe en el sistema");
        }
        return this.docenteMapper.toDto(this.crudDocente.findByNombre(nombre).orElse(null));
    }

    @Override
    public DocenteDto obtenerDocentePorCodigoUsuario(Long codigoUsuario) {
        if (codigoUsuario == null) return null;
        Optional<DocenteEntity> doc = this.crudDocente.findByCodigoUsuario(codigoUsuario);
        return this.docenteMapper.toDto(doc.orElse(null));
    }
}
