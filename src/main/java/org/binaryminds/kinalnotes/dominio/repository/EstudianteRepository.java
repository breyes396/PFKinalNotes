package org.binaryminds.kinalnotes.dominio.repository;

import org.binaryminds.kinalnotes.dominio.dto.ModEstudianteDto;
import org.binaryminds.kinalnotes.dominio.dto.EstudianteDto;

import java.util.List;

public interface EstudianteRepository {
    List<EstudianteDto> obtenerTodo();
    EstudianteDto obtenerEstudiantePorId(Long codigo);
    EstudianteDto guardarEstudiante(EstudianteDto estudianteDto);
    EstudianteDto actualizarEstudiante(Long codigo, ModEstudianteDto modEstudianteDto);
    void eliminarEstudiante(Long codigo);
    EstudianteDto obtenerEstudiantePorNombre(String nombre);
}
