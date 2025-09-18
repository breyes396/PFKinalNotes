package org.binaryminds.kinalnotes.persistence;

import org.binaryminds.kinalnotes.dominio.dto.EstudianteDto;
import org.binaryminds.kinalnotes.dominio.dto.ModEstudianteDto;
import org.binaryminds.kinalnotes.dominio.repository.EstudianteRepository;
import org.binaryminds.kinalnotes.persistence.crud.CrudEstudianteEntity;
import org.binaryminds.kinalnotes.persistence.entity.EstudianteEntity;
import org.binaryminds.kinalnotes.web.mapper.EstudianteMapper;
import org.springframework.stereotype.Repository;

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
            throw new IllegalArgumentException("El id no puede ser nulo");
        }
        return this.estudianteMapper.toDto(this.crudEstudiante.findById(id).orElse(null));
    }

    @Override
    public EstudianteDto guardarEstudiante(EstudianteDto estudianteDto){
        EstudianteEntity estudiante = this.estudianteMapper.toEntity(estudianteDto);
        this.crudEstudiante.save(estudiante);
        return this.estudianteMapper.toDto(estudiante);
    }

    @Override
    public EstudianteDto actualizarEstudiante(Long codigo, ModEstudianteDto modEstudianteDto){
        EstudianteEntity estudiante = this.crudEstudiante.findById(codigo).orElse(null);
        this.estudianteMapper.modificarEntityFromDto(modEstudianteDto, estudiante);
        return this.estudianteMapper.toDto(this.crudEstudiante.save(estudiante));
    }

    @Override
    public void eliminarEstudiante(Long id){
        EstudianteEntity estudianteEntity = this.crudEstudiante.findById(id).orElse(null);
        if (estudianteEntity == null){
            throw new IllegalArgumentException("El estudiante no puede ser eliminado");
        } else {
            this.crudEstudiante.deleteById(codigo);
        }
    }

    @Override
    public EstudianteDto obtenerEstudiantePorNombre(String nombre){
        if (nombre==null){
            throw new IllegalArgumentException("El nombre no puede ser nulo");
        }
        return this.estudianteMapper.toDto(this.crudEstudiante.findByNombre(nombre).orElse(null));
    }
}
