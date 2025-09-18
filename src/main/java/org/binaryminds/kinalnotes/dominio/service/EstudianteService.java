package org.binaryminds.kinalnotes.dominio.service;

import org.binaryminds.kinalnotes.dominio.repository.EstudianteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteService {
    private final EstudianteRepository estudianteRepository;

    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    public List<EstudianteDto> obtenerTodo(){
        return this.estudianteRepository.obtenerTodo();
    }

    public EstudianteDto obtenerEstudiantePorId(Long codigo){
        return this.estudianteRepository.obtenerEstudiantePorId(codigo);
    }

    public EstudianteDto guardarEstudiante(EstudianteDto estudianteDto){
        return this.estudianteRepository.guardarEstudiante(estudianteDto);
    }

    public EstudianteDto actualizarEstudiante(Long codigo, ModEstudianteDto modEstudianteDto){
        return this.estudianteRepository.actualizarEstudiante(codigo, modEstudianteDto);
    }

    public void eliminarEstudiante(Long codigo){
        this.estudianteRepository.eliminarEstudiante(codigo);
    }
}
