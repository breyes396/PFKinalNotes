package org.binaryminds.kinalnotes.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.binaryminds.kinalnotes.dominio.dto.DocenteDto;
import org.binaryminds.kinalnotes.dominio.dto.ModDocenteDto;
import org.binaryminds.kinalnotes.dominio.service.DocenteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/docentes")
@Tag(name = "Docentes", description = "Operaciones (CRUD) sobre los docentes de KinalNotes")
public class DocenteController {
    private final DocenteService docenteService;

    public DocenteController(DocenteService docenteService) {
        this.docenteService = docenteService;
    }

    @GetMapping
    @Operation(
            summary = "Obtener todos los docentes",
            description = "Retorna todos los docentes existentes en el sistema",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Docentes encontrados con exito"),
                    @ApiResponse(responseCode = "404", description = "Docentes no encontrados", content = @Content)
            }
    )
    public ResponseEntity<List<DocenteDto>> obtenerTodo(){
        return ResponseEntity.ok(this.docenteService.obtenerTodo());
    }

    @GetMapping("{codigo}")
    @Operation(
            summary = "Obtener un docente a partir de su identificador",
            description = "Retorna el docente que coincida con el identificador enviado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Docente fue encontrado con exito"),
                    @ApiResponse(responseCode = "404", description = "Docente no encontrado", content = @Content)
            }
    )
    public ResponseEntity<DocenteDto> obtenerDocentePorId(@Parameter(description = "Identificador de el docente a recuperar", example = "5")@PathVariable Long codigo){
        return ResponseEntity.ok(this.docenteService.obtenerDocentePorId(codigo));
    }

    @PostMapping
    @Operation(
            summary = "Agrega un nuevo docente",
            description = "Agrega un docente nuevo al sistema",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Docente agregado con exito"),
                    @ApiResponse(responseCode = "404", description = "Docente no agregado", content = @Content)
            }
    )
    public ResponseEntity<DocenteDto> guardarDocente(@RequestBody @Valid DocenteDto docenteDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.docenteService.guardarDocente(docenteDto));
    }

    @PutMapping("{codigo}")
    @Operation(
            summary = "Actualizar un docente existente",
            description = "Actualiza un docente del sistema ya existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Docente actualizado con exito"),
                    @ApiResponse(responseCode = "404", description = "Docente no encontrado o no actualizado", content = @Content)
            }
    )
    public ResponseEntity<DocenteDto> actualizarDocente(@Parameter(description = "Identificador de el docente a actualizar", example = "5")@PathVariable Long codigo, @RequestBody @Valid ModDocenteDto modDocenteDto){
        return ResponseEntity.ok(this.docenteService.actualizarDocente(codigo, modDocenteDto));
    }

    @DeleteMapping("{codigo}")
    @Operation(
            summary = "Eliminar un docente existente",
            description = "Elimina un docente del sistema ya existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Docente eliminado con exito"),
                    @ApiResponse(responseCode = "404", description = "Docente no encontrado o no eliminado", content = @Content)
            }
    )
    public ResponseEntity<DocenteDto> eliminarDocente(@Parameter(description = "Identificador de el docente a eliminar", example = "5")@PathVariable Long codigo){
        this.docenteService.eliminarDocente(codigo);
        return ResponseEntity.ok().build();
    }
}

