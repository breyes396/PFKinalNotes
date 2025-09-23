package org.binaryminds.kinalnotes.dominio.repository;

import org.binaryminds.kinalnotes.dominio.dto.ModCursoDto;
import org.binaryminds.kinalnotes.dominio.dto.CursoDto;

import java.util.List;

public interface CursoRepository {
    List<CursoDto> obtenerTodo();
    CursoDto obtenerCursoPorCodigo(Long codigo);
    CursoDto guardarCurso(CursoDto cursoDto);
    CursoDto actualizarCurso(Long codigo, ModCursoDto modCursoDto);
    void eliminarCurso(Long codigo);
    CursoDto obtenerCursoPorNombre(String nombre);
}
