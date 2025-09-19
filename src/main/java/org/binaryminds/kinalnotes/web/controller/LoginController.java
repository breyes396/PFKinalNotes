package org.binaryminds.kinalnotes.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.binaryminds.kinalnotes.dominio.dto.UsuarioDto;
import org.binaryminds.kinalnotes.dominio.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Autenticación", description = "Login de usuarios")
public class LoginController {

    private final UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario con email y contraseña")
    public ResponseEntity<?> login(
            @RequestParam String mail,
            @RequestParam String password) {

        try {
            UsuarioDto usuario = usuarioService.obtenerUsuarioPorCorreo(mail);

            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Usuario no encontrado");
            }

            if (!usuario.password().equals(password)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Contraseña incorrecta");
            }

            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Login exitoso");
            if (usuario.role().name().equals("ADMIN")) {
                response.put("mensaje", "Login exitoso - Bienvenido Administrador");
            } else if (usuario.role().name().equals("STUDENT")) {
                response.put("mensaje", "Login exitoso - Bienvenido Estudiante");
            } else if (usuario.role().name().equals("TEACHER")){
                response.put("mensaje", "Login exitoso - Bienvenido Docente");
            }
            response.put("codigo", usuario.codigo());
            response.put("role", usuario.role());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en el servidor: " + e.getMessage());
        }
    }

    @PostMapping("/registro")
    @Operation(summary = "Registrar usuario", description = "Crea un nuevo usuario (alias de guardarUsuario)")
    public ResponseEntity<UsuarioDto> registro(@RequestBody UsuarioDto usuarioDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.guardarUsuario(usuarioDto));
    }
}