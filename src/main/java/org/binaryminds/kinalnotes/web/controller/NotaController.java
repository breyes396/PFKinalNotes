package org.binaryminds.kinalnotes.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.binaryminds.kinalnotes.dominio.dto.NotaDto;
import org.binaryminds.kinalnotes.dominio.dto.ModNotaDto;
import org.binaryminds.kinalnotes.dominio.service.NotaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/notas")
@Tag(name = "Notas", description = "Operaciones (CRUD) sobre las notas de KinalNotes")
public class NotaController {
    private final NotaService notaService;

    public NotaController(NotaService notaService) {
        this.notaService = notaService;
    }

    @GetMapping
    @Operation(
            summary = "Obtener todas las notas",
            description = "Retorna todas las notas de los estudiantes",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Notas encontradas con exito"),
                    @ApiResponse(responseCode = "404", description = "Notas no encontradas", content = @Content)
            }
    )
    public ResponseEntity<List<NotaDto>> obtenerTodo(){
        return ResponseEntity.ok(this.notaService.obtenerTodo());
    }

    @GetMapping("{codigo}")
    @Operation(
            summary = "Obtener una nota a partir de su identificador",
            description = "Retorna la nota que coincida con el identificador enviado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Nota fue encontrada con exito"),
                    @ApiResponse(responseCode = "404", description = "Nota no encontrada", content = @Content)
            }
    )
    public ResponseEntity<NotaDto> obtenerNotaPorId(@Parameter(description = "Identificador de la nota a recuperar", example = "5")@PathVariable Long codigo){
        return ResponseEntity.ok(this.notaService.obtenerNotaPorCodigo(codigo));
    }

    @PostMapping
    @Operation(
            summary = "Agrega una nota",
            description = "Agrega una nota a un estudiante",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Nota agregada con exito"),
                    @ApiResponse(responseCode = "404", description = "Nota no agregada", content = @Content)
            }
    )
    public ResponseEntity<NotaDto> guardarNota(@RequestBody @Valid NotaDto notaDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.notaService.guardarNota(notaDto));
    }

    @PutMapping("{codigo}")
    @Operation(
            summary = "Actualizar una nota existente",
            description = "Actualiza una nota de un estudiante ya existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Nota actualizada con exito"),
                    @ApiResponse(responseCode = "404", description = "Nota no encontrada o no actualizada", content = @Content)
            }
    )
    public ResponseEntity<NotaDto> actualizarNota(@Parameter(description = "Identificador de la nota a actualizar", example = "5")@PathVariable Long codigo, @RequestBody @Valid ModNotaDto modNotaDto){
        return ResponseEntity.ok(this.notaService.actualizarNota(codigo, modNotaDto));
    }

    @DeleteMapping("{codigo}")
    @Operation(
            summary = "Eliminar una nota existente",
            description = "Elimina una nota de un estudiante ya existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Nota eliminada con exito"),
                    @ApiResponse(responseCode = "404", description = "Nota no encontrada o no eliminada", content = @Content)
            }
    )
    public ResponseEntity<NotaDto> eliminarNota(@Parameter(description = "Identificador de la nota a eliminar", example = "5")@PathVariable Long codigo){
        this.notaService.eliminarNota(codigo);
        return ResponseEntity.ok().build();
    }
}

