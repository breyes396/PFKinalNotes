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
@Tag(name = "Cursos", description = "OPERACIONES (CRUD) sobre los cursos disponibles de KinalNotes")
public class CursoController {
    private final CursoService cursoService;

    public CursoController(CursoService cursoService){
        this.cursoService = cursoService;
    }

    @GetMapping
    public ResponseEntity<List<CursoDto>> obtenerTodo(){
        return ResponseEntity.ok(this.cursoService.obtenerTodo());
    }

    @GetMapping("{codigo}")
    @Operation(
            summary = "Obtener un curso a partir de su identificador",
            description = "Retorna el curso que coincida con el identificador enviado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Curso fue encontrado con éxito"),
                    @ApiResponse(responseCode = "404", description = "Docente no encontrado", content = @Content)
            }
    )
    public ResponseEntity<CursoDto> obtenerCursoPorId(@Parameter(description = "Identificador del curso a recuperar", example = "5") @PathVariable Long codigo){
        return ResponseEntity.ok(this.cursoService.obtenerCursoPorCodigo(codigo));
    }

    @PostMapping
    public ResponseEntity<CursoDto> guardarCurso(@RequestBody @Valid CursoDto cursoDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.cursoService.guardarCurso(cursoDto));
    }

    @PutMapping("{codigo}")
    public ResponseEntity<CursoDto> actualizarCurso(@PathVariable Long codigo, @RequestBody @Valid ModCursoDto modCursoDto){
        return ResponseEntity.ok(this.cursoService.actualizarCurso(codigo, modCursoDto));
    }

    @DeleteMapping("{codigo}")
    public ResponseEntity<CursoDto> eliminarCurso(@PathVariable Long codigo){
        this.cursoService.eliminarCurso(codigo);
        return ResponseEntity.ok().build();
    }
}