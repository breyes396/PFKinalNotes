package org.binaryminds.kinalnotes.dominio.repository;

import org.binaryminds.kinalnotes.dominio.dto.ModDocenteDto;
import org.binaryminds.kinalnotes.dominio.dto.DocenteDto;

import java.util.List;

public interface DocenteRepository {
    List<DocenteDto> obtenerTodo();
    DocenteDto obtenerDocentePorId(Long id);
    DocenteDto guardarDocente(DocenteDto docenteDto);
    DocenteDto actualizarDocente(Long codigo, ModDocenteDto modDocenteDto);
    void eliminarDocente(Long codigo);
    DocenteDto obtenerDocentePorNombre(String nombre);
    DocenteDto obtenerDocentePorCodigoUsuario(Long codigoUsuario);
}
