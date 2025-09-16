package org.binaryminds.kinalnotes.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.binaryminds.kinalnotes.dominio.dto.ModUsuarioDto;
import org.binaryminds.kinalnotes.dominio.dto.UsuarioDto;
import org.binaryminds.kinalnotes.dominio.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/usuarios")
@Tag(name = "Usuarios", description = "OPERACIONES (CRUD) sobre los usuarios de KinalNotes")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDto>> obtenerTodo(){
        return ResponseEntity.ok(this.usuarioService.obtenerTodo());
    }

    @GetMapping("{codigo}")
    @Operation(
            summary = "Obtener un usuario a partir de su identificador",
            description = "Retorna el usuario que coincida con el identificador enviado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario fue encontrado con exito"),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
            }
    )
    public ResponseEntity<UsuarioDto> obtenerUsuarioPorId(@Parameter(description = "Identificador de el usuario a recuperar", example = "5")@PathVariable Long codigo){
        return ResponseEntity.ok(this.usuarioService.obtenerUsuarioPorId(codigo));
    }

    @PostMapping
    public ResponseEntity<UsuarioDto> guardarUsuario(@RequestBody UsuarioDto usuariosDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.usuarioService.guardarUsuario(usuariosDto));
    }

    @PutMapping("{codigo}")
    public ResponseEntity<UsuarioDto> actualizarUsuario(@PathVariable Long codigo, @RequestBody @Valid ModUsuarioDto modUsuarioDto){
        return ResponseEntity.ok(this.usuarioService.actualizarUsuario(codigo, modUsuarioDto));
    }

    @DeleteMapping("{codigo}")
    public ResponseEntity<UsuarioDto> eliminarUsuario(@PathVariable Long codigo){
        this.usuarioService.eliminarUsuario(codigo);
        return ResponseEntity.ok().build();
    }
}
