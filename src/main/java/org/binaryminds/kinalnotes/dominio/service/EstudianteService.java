package org.binaryminds.kinalnotes.dominio.service;

import org.binaryminds.kinalnotes.dominio.dto.CursoDto;
import org.binaryminds.kinalnotes.dominio.dto.EstudianteDto;
import org.binaryminds.kinalnotes.dominio.dto.ModEstudianteDto;
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

    public List<EstudianteDto> obtenerEstudiantesPorCurso(Long codigoCurso){
        return this.estudianteRepository.obtenerEstudiantesPorCurso(codigoCurso);
    }

    // Nuevos métodos
    public EstudianteDto obtenerEstudiantePorCodigoUsuario(Long codigoUsuario){
        return this.estudianteRepository.obtenerEstudiantePorCodigoUsuario(codigoUsuario);
    }
    public void inscribirEnCurso(Long estudianteCodigo, Long cursoCodigo){
        this.estudianteRepository.inscribirEnCurso(estudianteCodigo, cursoCodigo);
    }
    public void darseDeBajaCurso(Long estudianteCodigo, Long cursoCodigo){
        this.estudianteRepository.darseDeBajaCurso(estudianteCodigo, cursoCodigo);
    }
    public List<CursoDto> obtenerCursosInscritos(Long estudianteCodigo){
        return this.estudianteRepository.obtenerCursosInscritos(estudianteCodigo);
    }
    public List<CursoDto> obtenerCursosDisponibles(Long estudianteCodigo){
        return this.estudianteRepository.obtenerCursosDisponibles(estudianteCodigo);
    }
}
