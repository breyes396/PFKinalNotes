package org.binaryminds.kinalnotes.dominio.service;

import org.binaryminds.kinalnotes.dominio.dto.NotaDto;
import org.binaryminds.kinalnotes.dominio.dto.ModNotaDto;
import org.binaryminds.kinalnotes.dominio.repository.NotaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotaService {
    private final NotaRepository notaRepository;

    public NotaService(NotaRepository notaRepository) {
        this.notaRepository = notaRepository;
    }

    @Transactional(readOnly = true)
    public List<NotaDto> obtenerTodo(){
        return this.notaRepository.obtenerTodo();
    }

    @Transactional(readOnly = true)
    public NotaDto obtenerNotaPorCodigo(Long codigo){
        return this.notaRepository.obtenerNotaPorCodigo(codigo);
    }

    @Transactional
    public NotaDto guardarNota(NotaDto notaDto){
        return this.notaRepository.guardarNota(notaDto);
    }

    @Transactional
    public NotaDto actualizarNota(Long codigo, ModNotaDto modNotaDto){
        return this.notaRepository.actualizarNota(codigo, modNotaDto);
    }

    @Transactional
    public void eliminarNota(Long codigo){
        this.notaRepository.eliminarNota(codigo);
    }

    @Transactional(readOnly = true)
    public NotaDto obtenerNotaPorCalificacion(Integer calificacion) {
        return this.notaRepository.obtenerNotaPorCalificacion(calificacion);
    }

    @Transactional(readOnly = true)
    public NotaDto obtenerNotaPorEstudianteCursoDocente(Long estudiante, Long curso, Long docente){
        return this.notaRepository.obtenerNotaPorEstudianteCursoDocente(estudiante, curso, docente);
    }

    @Transactional(readOnly = true)
    public List<NotaDto> obtenerNotasPorEstudiante(Long estudiante){
        return this.notaRepository.obtenerNotasPorEstudiante(estudiante);
    }
}
