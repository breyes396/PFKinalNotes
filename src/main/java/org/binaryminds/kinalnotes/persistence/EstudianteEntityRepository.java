package org.binaryminds.kinalnotes.persistence;

import org.binaryminds.kinalnotes.dominio.dto.EstudianteDto;
import org.binaryminds.kinalnotes.dominio.dto.ModEstudianteDto;
import org.binaryminds.kinalnotes.dominio.exception.EstudianteNoExisteException;
import org.binaryminds.kinalnotes.dominio.exception.EstudianteYaExisteException;
import org.binaryminds.kinalnotes.dominio.repository.EstudianteRepository;
import org.binaryminds.kinalnotes.persistence.crud.CrudEstudianteEntity;
import org.binaryminds.kinalnotes.persistence.entity.EstudianteEntity;
import org.binaryminds.kinalnotes.web.mapper.EstudianteMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class EstudianteEntityRepository implements EstudianteRepository{

    private final CrudEstudianteEntity crudEstudiante;
    private final EstudianteMapper estudianteMapper;

    public EstudianteEntityRepository(CrudEstudianteEntity crudEstudiante, EstudianteMapper estudianteMapper){
        this.crudEstudiante = crudEstudiante;
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
}
