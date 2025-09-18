package org.binaryminds.kinalnotes.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.binaryminds.kinalnotes.dominio.dto.EstudianteDto;
import org.binaryminds.kinalnotes.dominio.dto.ModEstudianteDto;
import org.binaryminds.kinalnotes.dominio.service.EstudianteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/estudiantes")
@Tag(name = "Estudiantes", description = "OPERACIONES (CRUD) sobre los estudiantes de KinalNotes")
public class EstudianteController {
    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService){
        this.estudianteService = estudianteService;
    }

    @GetMapping
    public ResponseEntity<List<EstudianteDto>> obtenerTodo(){
        return ResponseEntity.ok(this.estudianteService.obtenerTodo());
    }

    @GetMapping("{codigo}")
    @Operation(
            summary = "Obtener un estudiante a partir de su identificador",
            description = "Retorna el estudiante que coincida con el identificador enviado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Estudiante fue encontrado con éxito"),
                    @ApiResponse(responseCode = "404", description = "Docente no encontrado", content = @Content)
            }
    )
    public ResponseEntity<EstudianteDto> obtenerEstudiantePorId(@Parameter(description = "Identificador del estudiante a recuperar", example = "5") @PathVariable Long codigo){
        return ResponseEntity.ok(this.estudianteService.obtenerEstudiantePorId(codigo));
    }

    @PostMapping
    public ResponseEntity<EstudianteDto> guardarEstudiante(@RequestBody EstudianteDto estudianteDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.estudianteService.guardarEstudiante(estudianteDto));
    }

    @PutMapping("{codigo}")
    public ResponseEntity<EstudianteDto> actualizarEstudiante(@PathVariable Long codigo, @RequestBody @Valid ModEstudianteDto modEstudianteDto){
        return ResponseEntity.ok(this.estudianteService.actualizarEstudiante(codigo, modEstudianteDto));
    }

    @DeleteMapping("{codigo}")
    public ResponseEntity<EstudianteDto> eliminarEstudiante(@PathVariable Long codigo){
        this.estudianteService.eliminarEstudiante(codigo);
        return ResponseEntity.ok().build();
    }
}
