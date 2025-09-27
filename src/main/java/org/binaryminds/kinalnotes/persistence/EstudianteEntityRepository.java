package org.binaryminds.kinalnotes.persistence;

import org.binaryminds.kinalnotes.dominio.dto.EstudianteDto;
import org.binaryminds.kinalnotes.dominio.dto.ModEstudianteDto;
import org.binaryminds.kinalnotes.dominio.exception.EstudianteNoExisteException;
import org.binaryminds.kinalnotes.dominio.exception.EstudianteYaExisteException;
import org.binaryminds.kinalnotes.dominio.repository.EstudianteRepository;
import org.binaryminds.kinalnotes.persistence.crud.CrudEstudianteEntity;
import org.binaryminds.kinalnotes.persistence.crud.CrudCursoEntity;
import org.binaryminds.kinalnotes.persistence.entity.CursoEntity;
import org.binaryminds.kinalnotes.persistence.entity.EstudianteEntity;
import org.binaryminds.kinalnotes.web.mapper.EstudianteMapper;
import org.binaryminds.kinalnotes.web.mapper.CursoMapper;
import org.binaryminds.kinalnotes.dominio.dto.CursoDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EstudianteEntityRepository implements EstudianteRepository{

    private final CrudEstudianteEntity crudEstudiante;
    private final EstudianteMapper estudianteMapper;
    private final CrudCursoEntity crudCurso;
    private final CursoMapper cursoMapper;

    public EstudianteEntityRepository(CrudEstudianteEntity crudEstudiante, EstudianteMapper estudianteMapper, CrudCursoEntity crudCurso, CursoMapper cursoMapper){
        this.crudEstudiante = crudEstudiante;
        this.estudianteMapper = estudianteMapper;
        this.crudCurso = crudCurso;
        this.cursoMapper = cursoMapper;
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
            throw new EstudianteNoExisteException("El estudiante: "  + nombre + " no existe");
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
        Optional<EstudianteEntity> est = this.crudEstudiante.findByUsuario_Codigo(codigoUsuario);
        return this.estudianteMapper.toDto(est.orElse(null));
    }

    @Override
    public void inscribirEnCurso(Long estudianteCodigo, Long cursoCodigo) {
        if(estudianteCodigo==null || cursoCodigo==null) return;
        EstudianteEntity estudiante = crudEstudiante.findById(estudianteCodigo).orElse(null);
        CursoEntity curso = crudCurso.findById(cursoCodigo).orElse(null);
        if(estudiante==null || curso==null) return;
        List<CursoEntity> cursos = estudiante.getCursos();
        if(cursos == null){
            cursos = new ArrayList<>();
            estudiante.setCursos(cursos);
        }
        boolean yaInscrito = cursos.stream().anyMatch(c -> c.getCodigo().equals(cursoCodigo));
        if(!yaInscrito){
            cursos.add(curso);
            crudEstudiante.save(estudiante);
        }
    }

    @Override
    public void darseDeBajaCurso(Long estudianteCodigo, Long cursoCodigo) {
        if(estudianteCodigo==null || cursoCodigo==null) return;
        EstudianteEntity estudiante = crudEstudiante.findById(estudianteCodigo).orElse(null);
        if(estudiante==null || estudiante.getCursos()==null) return;
        boolean removed = estudiante.getCursos().removeIf(c -> c.getCodigo().equals(cursoCodigo));
        if(removed){
            crudEstudiante.save(estudiante);
        }
    }

    @Override
    public List<CursoDto> obtenerCursosInscritos(Long estudianteCodigo) {
        if(estudianteCodigo==null) return List.of();
        EstudianteEntity estudiante = crudEstudiante.findById(estudianteCodigo).orElse(null);
        if(estudiante==null || estudiante.getCursos()==null) return List.of();
        return cursoMapper.toDto(estudiante.getCursos());
    }

    @Override
    public List<CursoDto> obtenerCursosDisponibles(Long estudianteCodigo) {
        // Lista de todos los cursos marcando cuales están inscritos (se manejará en la vista)
        List<CursoEntity> todos = new ArrayList<>();
        crudCurso.findAll().forEach(todos::add);
        return cursoMapper.toDto(todos);
    }
}
