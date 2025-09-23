package org.binaryminds.kinalnotes.dominio.service;

import org.binaryminds.kinalnotes.dominio.dto.CursoDto;
import org.binaryminds.kinalnotes.dominio.dto.ModCursoDto;
import org.binaryminds.kinalnotes.dominio.repository.CursoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CursoService {
    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public List<CursoDto> obtenerTodo(){
        return this.cursoRepository.obtenerTodo();
    }
    public CursoDto obtenerCursoPorCodigo(Long codigo){
        return this.cursoRepository.obtenerCursoPorCodigo(codigo);
    }
    public CursoDto guardarCurso(CursoDto cursoDto){
        return this.cursoRepository.guardarCurso(cursoDto);
    }
    public CursoDto actualizarCurso(Long codigo, ModCursoDto modCursoDto){
        return this.cursoRepository.actualizarCurso(codigo, modCursoDto);
    }
    public void eliminarCurso(Long codigo){
        this.cursoRepository.eliminarCurso(codigo);
    }
}
