package org.binaryminds.kinalnotes.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.binaryminds.kinalnotes.dominio.dto.CursoDto;
import org.binaryminds.kinalnotes.dominio.dto.ModCursoDto;
import org.binaryminds.kinalnotes.dominio.service.CursoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/cursos")
@Tag(name = "Cursos", description = "Operaciones (CRUD) sobre los cursos disponibles de KinalNotes")
public class CursoController {
    private final CursoService cursoService;

    public CursoController(CursoService cursoService){
        this.cursoService = cursoService;
    }

    @GetMapping
    @Operation(
            summary = "Obtener todos los cursos",
            description = "Retorna todos los cursos existentes",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cursos encontrados con exito"),
                    @ApiResponse(responseCode = "404", description = "Cursos no encontrados", content = @Content)
            }
    )
    public ResponseEntity<List<CursoDto>> obtenerTodo(){
        return ResponseEntity.ok(this.cursoService.obtenerTodo());
    }

    @GetMapping("{codigo}")
    @Operation(
            summary = "Obtener un curso a partir de su identificador",
            description = "Retorna el curso que coincida con el identificador enviado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Curso fue encontrado con éxito"),
                    @ApiResponse(responseCode = "404", description = "Curso no encontrado", content = @Content)
            }
    )
    public ResponseEntity<CursoDto> obtenerCursoPorId(@Parameter(description = "Identificador del curso a recuperar", example = "5") @PathVariable Long codigo){
        return ResponseEntity.ok(this.cursoService.obtenerCursoPorCodigo(codigo));
    }

    @PostMapping
    @Operation(
            summary = "Agrega un curso",
            description = "Agrega un curso nuevo al sistema",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Curso agregado con exito"),
                    @ApiResponse(responseCode = "404", description = "Curso no agregado", content = @Content)
            }
    )
    public ResponseEntity<CursoDto> guardarCurso(@RequestBody @Valid CursoDto cursoDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.cursoService.guardarCurso(cursoDto));
    }

    @PutMapping("{codigo}")
    @Operation(
            summary = "Actualizar un curso existente",
            description = "Actualiza un curso del sistema ya existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Curso actualizado con exito"),
                    @ApiResponse(responseCode = "404", description = "Curso no encontrado o no actualizado", content = @Content)
            }
    )
    public ResponseEntity<CursoDto> actualizarCurso(@Parameter(description = "Identificador de el curso a actualizar", example = "5")@PathVariable Long codigo, @RequestBody @Valid ModCursoDto modCursoDto){
        return ResponseEntity.ok(this.cursoService.actualizarCurso(codigo, modCursoDto));
    }

    @DeleteMapping("{codigo}")
    @Operation(
            summary = "Eliminar un curso existente",
            description = "Elimina un curso del sistema ya existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Curso eliminado con exito"),
                    @ApiResponse(responseCode = "404", description = "Curso no encontrado o no eliminado", content = @Content)
            }
    )
    public ResponseEntity<CursoDto> eliminarCurso(@Parameter(description = "Identificador de el curso a eliminar", example = "5")@PathVariable Long codigo){
        this.cursoService.eliminarCurso(codigo);
        return ResponseEntity.ok().build();
    }
}