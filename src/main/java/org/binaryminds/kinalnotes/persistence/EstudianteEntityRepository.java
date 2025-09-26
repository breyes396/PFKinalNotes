package org.binaryminds.kinalnotes.persistence;

import org.binaryminds.kinalnotes.dominio.dto.EstudianteDto;
import org.binaryminds.kinalnotes.dominio.dto.ModEstudianteDto;
import org.binaryminds.kinalnotes.dominio.exception.EstudianteNoExisteException;
import org.binaryminds.kinalnotes.dominio.exception.EstudianteYaExisteException;
import org.binaryminds.kinalnotes.dominio.repository.EstudianteRepository;
import org.binaryminds.kinalnotes.persistence.crud.CrudCursoEntity;
import org.binaryminds.kinalnotes.persistence.crud.CrudEstudianteEntity;
import org.binaryminds.kinalnotes.persistence.entity.CursoEntity;
import org.binaryminds.kinalnotes.persistence.entity.EstudianteEntity;
import org.binaryminds.kinalnotes.web.mapper.EstudianteMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EstudianteEntityRepository implements EstudianteRepository{

    private final CrudEstudianteEntity crudEstudiante;
    private final CrudCursoEntity crudCurso;
    private final EstudianteMapper estudianteMapper;

    public EstudianteEntityRepository(CrudEstudianteEntity crudEstudiante, CrudCursoEntity crudCurso, EstudianteMapper estudianteMapper){
        this.crudEstudiante = crudEstudiante;
        this.crudCurso = crudCurso;
        this.estudianteMapper = estudianteMapper;
    }

    @Override
    public List<EstudianteDto> obtenerTodo(){
        return this.estudianteMapper.toDto(this.crudEstudiante.findAll());
    }

    @Override
    public EstudianteDto obtenerEstudiantePorId(Long id){
        if (id==null){
            throw new EstudianteNoExisteException("El estudiante con codigo: " + id + " no existe en el sistema.");
        }
        return this.estudianteMapper.toDto(this.crudEstudiante.findById(id).orElse(null));
    }

    @Override
    public EstudianteDto guardarEstudiante(EstudianteDto estudianteDto){
        if (this.crudEstudiante.findFirstByNombre(estudianteDto.name())!=null){
            throw new EstudianteYaExisteException("El estudiante: " + estudianteDto.name() + " ya existe");
        }
        EstudianteEntity estudiante = this.estudianteMapper.toEntity(estudianteDto);
        this.crudEstudiante.save(estudiante);
        return this.estudianteMapper.toDto(estudiante);
    }

    @Override
    public EstudianteDto actualizarEstudiante(Long codigo, ModEstudianteDto modEstudianteDto){
        EstudianteEntity estudiante = this.crudEstudiante.findById(codigo).orElse(null);
        if (estudiante==null){
            throw new EstudianteNoExisteException("El estudiante con codigo: " + codigo + " no existe");
        } else {
            this.estudianteMapper.modificarEntityFromDto(modEstudianteDto, estudiante);
            return this.estudianteMapper.toDto(this.crudEstudiante.save(estudiante));
        }

    }

    @Override
    public void eliminarEstudiante(Long id){
        EstudianteEntity estudianteEntity = this.crudEstudiante.findById(id).orElse(null);
        if (estudianteEntity == null){
            throw new EstudianteNoExisteException("El estudiante con codigo: " + id + " no existe");
        } else {
            this.crudEstudiante.deleteById(id);
        }
    }

    @Override
    public EstudianteDto obtenerEstudiantePorNombre(String nombre){
        if (nombre==null){
            throw new EstudianteNoExisteException("El estudiante: " +  nombre + " no existe");
        }
        return this.estudianteMapper.toDto(this.crudEstudiante.findByNombre(nombre).orElse(null));
    }

    @Override
    public List<EstudianteDto> obtenerEstudiantesPorCurso(Long codigoCurso) {
        if (codigoCurso == null) return List.of();
        return this.estudianteMapper.toDto(
                this.crudEstudiante.findByCursos_Codigo(codigoCurso)
        );
    }

    @Override
    public EstudianteDto obtenerEstudiantePorCodigoUsuario(Long codigoUsuario) {
        if(codigoUsuario == null) return null;
        return this.estudianteMapper.toDto(this.crudEstudiante.findByUsuario_Codigo(codigoUsuario).orElse(null));
    }

    @Override
    public EstudianteDto inscribirEstudianteEnCurso(Long estudianteId, Long cursoId) {
        if(estudianteId == null || cursoId == null) return null;
        EstudianteEntity estudiante = crudEstudiante.findById(estudianteId).orElseThrow(() -> new EstudianteNoExisteException("Estudiante no existe"));
        CursoEntity curso = crudCurso.findById(cursoId).orElse(null);
        if(curso == null) throw new IllegalArgumentException("Curso no existe");
        if(estudiante.getCursos() == null) estudiante.setCursos(new ArrayList<>());
        boolean yaInscrito = estudiante.getCursos().stream().anyMatch(c -> c.getCodigo().equals(cursoId));
        if(!yaInscrito){
            estudiante.getCursos().add(curso);
            estudiante = crudEstudiante.save(estudiante);
        }
        return estudianteMapper.toDto(estudiante);
    }
}
