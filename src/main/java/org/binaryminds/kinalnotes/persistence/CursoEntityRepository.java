package org.binaryminds.kinalnotes.persistence;

import org.binaryminds.kinalnotes.dominio.dto.CursoDto;
import org.binaryminds.kinalnotes.dominio.dto.ModCursoDto;
import org.binaryminds.kinalnotes.dominio.exception.CursoNoExisteException;
import org.binaryminds.kinalnotes.dominio.exception.CursoYaExisteException;
import org.binaryminds.kinalnotes.dominio.exception.DocenteNoExisteException;
import org.binaryminds.kinalnotes.dominio.exception.DocenteYaExisteException;
import org.binaryminds.kinalnotes.dominio.repository.CursoRepository;
import org.binaryminds.kinalnotes.persistence.crud.CrudCursoEntity;
import org.binaryminds.kinalnotes.persistence.entity.CursoEntity;
import org.binaryminds.kinalnotes.persistence.entity.DocenteEntity;
import org.binaryminds.kinalnotes.web.mapper.CursoMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class CursoEntityRepository implements CursoRepository {

    private final CurdCursoEntity crudCurso;
    private final CursoMapper cursoMapper;

    public CursoEntityRepository(CrudCursoEntity crudCurso, CursoMapper cursoMapper){
        this.crudCurso=crudCurso;
        this.cursoMapper=cursoMapper;
    }

    @Override
    public List<CursoDto> obtenerTodo() {
        return this.cursoMapper.toDto(this.crudCurso.findAll());
    }

    @Override
    public CursoDto obtenerCursoPorCodigo(Long codigo) {
        return this.cursoMapper.toDto(this.crudCurso.findById(Id).orElse(null));
    }

    @Override
    public CursoDto guardarCurso(CursoDto cursoDto) {
        if (this.crudCurso.findFirstByNombre(cursoDto.name()) != null) {
            throw new CursoYaExisteException(cursoDto.name());
        } else {
            CursoEntity curso = this.cursoMapper.toEntity(cursoDto);
            this.crudCurso.save(curso);
            return this.cursoMapper.toDto(curso);
        }
    }

    @Override
    public CursoDto actualizarCurso(Long codigo, ModCursoDto modCursoDto) {
        CursoEntity curso = this.crudCurso.findById(codigo).orElse(null);
        if (curso == null) {
            throw new CursoNoExisteException(codigo);
        }
        this.cursoMapper.modificarEntityFromDto(modCursoDto, curso);
        return this.cursoMapper.toDto(this.crudCurso.save(curso));
    }

    @Override
    public void eliminarCurso(Long codigo) {
        CursoEntity cursoEntity = this.crudCurso.findById(codigo).orElse(null);
        if (cursoEntity == null) {
            throw new CursoNoExisteException(codigo);
        } else {
            this.crudCurso.deleteById(codigo);
        }
    }

    @Override
    public CursoDto obtenerCursoPorNombre(String nombre) {
        if (nombre == null) {
            throw new IllegalArgumentException("El nombre no existe en el sistema");
        }
        return this.cursoMapper.toDto(this.crudCurso.findByNombre(nombre).orElse(null));
    }
}
