package org.binaryminds.kinalnotes.dominio.service;

import org.binaryminds.kinalnotes.dominio.dto.DocenteDto;
import org.binaryminds.kinalnotes.dominio.dto.ModDocenteDto;
import org.binaryminds.kinalnotes.dominio.repository.DocenteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocenteService {
    private final DocenteRepository docenteRepository;

    public DocenteService(DocenteRepository docenteRepository) {
        this.docenteRepository = docenteRepository;
    }

    public List<DocenteDto> obtenerTodo(){
        return this.docenteRepository.obtenerTodo();
    }

    public DocenteDto obtenerDocentePorId(Long codigo){
        return this.docenteRepository.obtenerDocentePorId(codigo);
    }

    public DocenteDto guardarDocente(DocenteDto docenteDto){
        return this.docenteRepository.guardarDocente(docenteDto);
    }
    public DocenteDto actualizarDocente(Long codigo, ModDocenteDto modDocenteDto){
        return this.docenteRepository.actualizarDocente(codigo, modDocenteDto);
    }
    public void eliminarDocente(Long codigo){
        this.docenteRepository.eliminarDocente(codigo);
    }
    public DocenteDto obtenerDocentePorCodigoUsuario(Long codigoUsuario){
        return this.docenteRepository.obtenerDocentePorCodigoUsuario(codigoUsuario);
    }
    public DocenteDto obtenerDocentePorCodigoCurso(Long codigoCurso){
        return this.docenteRepository.obtenerDocentePorCodigoCurso(codigoCurso);
    }
}
