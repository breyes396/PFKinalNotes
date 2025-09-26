package org.binaryminds.kinalnotes.dominio.repository;

import org.binaryminds.kinalnotes.dominio.dto.ModNotaDto;
import org.binaryminds.kinalnotes.dominio.dto.NotaDto;

import java.util.List;

public interface NotaRepository {
    List<NotaDto> obtenerTodo();
    NotaDto obtenerNotaPorCodigo(Long codigo);
    NotaDto guardarNota(NotaDto notaDto);
    NotaDto actualizarNota(Long codigo, ModNotaDto modNotaDto);
    void eliminarNota(Long codigo);
    NotaDto obtenerNotaPorCalificacion(Integer calificacion);
    NotaDto obtenerNotaPorEstudianteCursoDocente(Long codigoEstudiante, Long codigoCurso, Long codigoDocente);
}
