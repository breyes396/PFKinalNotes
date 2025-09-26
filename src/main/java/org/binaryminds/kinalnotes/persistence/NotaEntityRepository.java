package org.binaryminds.kinalnotes.persistence;

import org.binaryminds.kinalnotes.dominio.dto.NotaDto;
import org.binaryminds.kinalnotes.dominio.dto.ModNotaDto;
import org.binaryminds.kinalnotes.dominio.exception.CalificacionNoExisteException;
import org.binaryminds.kinalnotes.dominio.exception.NotaNoExisteException;
import org.binaryminds.kinalnotes.dominio.repository.NotaRepository;
import org.binaryminds.kinalnotes.persistence.crud.CrudNotaEntity;
import org.binaryminds.kinalnotes.persistence.entity.NotaEntity;
import org.binaryminds.kinalnotes.web.mapper.NotaMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class NotaEntityRepository implements NotaRepository {
    private final CrudNotaEntity crudNota;
    private final NotaMapper notaMapper;

    public NotaEntityRepository(CrudNotaEntity crudNota, NotaMapper notaMapper){
        this.crudNota = crudNota;
        this.notaMapper = notaMapper;
    }

    @Override
    public List<NotaDto> obtenerTodo(){
        return this.notaMapper.toDto(this.crudNota.findAll());
    }

    @Override
    public NotaDto obtenerNotaPorCodigo(Long codigo){
        if (codigo == null){
            throw new NotaNoExisteException(codigo);
        }
        return this.notaMapper.toDto(this.crudNota.findById(codigo).orElse(null));
    }

    @Override
    public NotaDto guardarNota(NotaDto notaDto){
        NotaEntity nota = this.notaMapper.toEntity(notaDto);
        this.crudNota.save(nota);
        return this.notaMapper.toDto(nota);
    }

    @Override
    public NotaDto actualizarNota(Long codigo, ModNotaDto modNotaDto){
        NotaEntity nota = this.crudNota.findById(codigo).orElse(null);
        if (nota == null){
            throw new NotaNoExisteException(codigo);
        } else {
            this.notaMapper.modificarEntityFromDto(modNotaDto, nota);
            return this.notaMapper.toDto(this.crudNota.save(nota));
        }
    }

    @Override
    public void eliminarNota(Long codigo){
        NotaEntity notaEntity = this.crudNota.findById(codigo).orElse(null);
        if (notaEntity == null){
            throw new NotaNoExisteException(codigo);
        } else {
            this.crudNota.deleteById(codigo);
        }
    }

    @Override
    public NotaDto obtenerNotaPorCalificacion(Integer calificacion) {
        if (this.crudNota.findByCalificacion(calificacion) == null){
            throw new CalificacionNoExisteException(calificacion);
        }
        return this.notaMapper.toDto(this.crudNota.findByCalificacion(calificacion).orElse(null));
    }

    @Override
    public NotaDto obtenerNotaPorEstudianteCursoDocente(Long codigoEstudiante, Long codigoCurso, Long codigoDocente) {
        if(codigoEstudiante==null || codigoCurso==null || codigoDocente==null) return null;
        Optional<NotaEntity> nota = this.crudNota.findByCodigoEstudianteAndCodigoCursoAndCodigoDocente(codigoEstudiante,codigoCurso,codigoDocente);
        return this.notaMapper.toDto(nota.orElse(null));
    }
}
