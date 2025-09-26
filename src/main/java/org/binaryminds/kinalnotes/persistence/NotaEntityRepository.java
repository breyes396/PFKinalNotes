package org.binaryminds.kinalnotes.persistence;

import org.binaryminds.kinalnotes.dominio.dto.NotaDto;
import org.binaryminds.kinalnotes.dominio.dto.ModNotaDto;
import org.binaryminds.kinalnotes.dominio.exception.CalificacionNoExisteException;
import org.binaryminds.kinalnotes.dominio.exception.NotaNoExisteException;
import org.binaryminds.kinalnotes.dominio.repository.NotaRepository;
import org.binaryminds.kinalnotes.persistence.crud.CrudNotaEntity;
import org.binaryminds.kinalnotes.persistence.entity.NotaEntity;
import org.binaryminds.kinalnotes.web.mapper.NotaMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
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
        if(notaDto == null){
            log.warn("guardarNota llamado con notaDto null");
            return null;
        }
        Optional<NotaEntity> existente = crudNota.findByCodigoEstudianteAndCodigoCursoAndCodigoDocente(
                notaDto.codigo_estudiante(), notaDto.codigo_curso(), notaDto.codigo_docente());
        if(existente.isPresent()){
            log.debug("Nota existente encontrada (reutilizando) codigo={} -> se actualizará calificacion {}", existente.get().getCodigo(), notaDto.calificacion());
            NotaEntity n = existente.get();
            n.setCalificacion(notaDto.calificacion());
            n.setFecha(LocalDate.now());
            return notaMapper.toDto(crudNota.save(n));
        }
        NotaEntity nota = this.notaMapper.toEntity(notaDto);
        try {
            this.crudNota.save(nota);
            log.debug("Nota creada codigo={} estudiante={} curso={} docente={} calificacion={}", nota.getCodigo(), nota.getCodigoEstudiante(), nota.getCodigoCurso(), nota.getCodigoDocente(), nota.getCalificacion());
            return this.notaMapper.toDto(nota);
        } catch (DataIntegrityViolationException dive){
            log.error("Violación de integridad al guardar nota (posible duplicado). Se intentará recuperación.", dive);
            existente = crudNota.findByCodigoEstudianteAndCodigoCursoAndCodigoDocente(
                    notaDto.codigo_estudiante(), notaDto.codigo_curso(), notaDto.codigo_docente());
            return existente.map(notaMapper::toDto).orElse(null);
        }
    }

    @Override
    public NotaDto actualizarNota(Long codigo, ModNotaDto modNotaDto){
        NotaEntity nota = this.crudNota.findById(codigo).orElse(null);
        if (nota == null){
            log.warn("Intento de actualizar nota inexistente codigo={}", codigo);
            throw new NotaNoExisteException(codigo);
        } else {
            log.debug("Actualizando nota codigo={} nuevaCalificacion={}", codigo, modNotaDto.calificacion());
            this.notaMapper.modificarEntityFromDto(modNotaDto, nota);
            nota.setFecha(LocalDate.now());
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
