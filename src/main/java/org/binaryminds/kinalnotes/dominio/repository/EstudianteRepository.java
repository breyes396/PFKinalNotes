package org.binaryminds.kinalnotes.dominio.repository;

import org.binaryminds.kinalnotes.dominio.dto.ModEstudianteDto;
import org.binaryminds.kinalnotes.dominio.dto.EstudianteDto;
import org.binaryminds.kinalnotes.dominio.dto.CursoDto;

import java.util.List;

public interface EstudianteRepository {
    List<EstudianteDto> obtenerTodo();
    EstudianteDto obtenerEstudiantePorId(Long codigo);
    EstudianteDto guardarEstudiante(EstudianteDto estudianteDto);
    EstudianteDto actualizarEstudiante(Long codigo, ModEstudianteDto modEstudianteDto);
    void eliminarEstudiante(Long codigo);
    EstudianteDto obtenerEstudiantePorNombre(String nombre);
    List<EstudianteDto> obtenerEstudiantesPorCurso(Long codigoCurso);
    // Nuevos métodos
    EstudianteDto obtenerEstudiantePorCodigoUsuario(Long codigoUsuario);
    void inscribirEnCurso(Long estudianteCodigo, Long cursoCodigo);
    void darseDeBajaCurso(Long estudianteCodigo, Long cursoCodigo);
    List<CursoDto> obtenerCursosInscritos(Long estudianteCodigo);
    List<CursoDto> obtenerCursosDisponibles(Long estudianteCodigo);
}
