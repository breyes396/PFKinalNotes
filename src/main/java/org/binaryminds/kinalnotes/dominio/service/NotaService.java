package org.binaryminds.kinalnotes.dominio.service;

import org.binaryminds.kinalnotes.dominio.dto.NotaDto;
import org.binaryminds.kinalnotes.dominio.dto.ModNotaDto;
import org.binaryminds.kinalnotes.dominio.repository.NotaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotaService {
    private final NotaRepository notaRepository;

    public NotaService(NotaRepository notaRepository) {
        this.notaRepository = notaRepository;
    }

    public List<NotaDto> obtenerTodo(){
        return this.notaRepository.obtenerTodo();
    }

    public NotaDto obtenerNotaPorCodigo(Long codigo){
        return this.notaRepository.obtenerNotaPorCodigo(codigo);
    }

    public NotaDto guardarNota(NotaDto notaDto){
        return this.notaRepository.guardarNota(notaDto);
    }

    public NotaDto actualizarDto(Long codigo, ModNotaDto modNotaDto){
        return this.notaRepository.actualizarNota(codigo, modNotaDto);
    }

    public void eliminarNota(Long codigo){
        this.notaRepository.eliminarNota(codigo);
    }

    public NotaDto obtenerNotaPorNombre(String nombre){
        return this.notaRepository.obtenerNotaPorNombre(nombre);
    }
}
