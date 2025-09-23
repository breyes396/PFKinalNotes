package org.binaryminds.kinalnotes.web.exception;

import org.binaryminds.kinalnotes.dominio.exception.*;
import org.binaryminds.kinalnotes.dominio.exception.Error;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(UsuarioYaExisteException.class)
    public ResponseEntity<Error> handlerException(UsuarioYaExisteException ex){
        Error error = new Error("Usuario-ya-existe", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(UsuarioNoExisteException.class)
    public ResponseEntity<Error> handlerException(UsuarioNoExisteException ex){
        Error error = new Error("Usuario-no-existe", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
    @ExceptionHandler(DocenteYaExisteException.class)
    public ResponseEntity<Error> handlerException(DocenteYaExisteException ex){
        Error error = new Error("Docente-ya-existe", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(DocenteNoExisteException.class)
    public ResponseEntity<Error> handlerException(DocenteNoExisteException ex){
        Error error = new Error("Docente-no-existe", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(EstudianteYaExisteException.class)
    public ResponseEntity<Error> handlerException(EstudianteYaExisteException ex){
        Error error = new Error("Estudiante-ya-existe", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(EstudianteNoExisteException.class)
    public ResponseEntity<Error> handlerException(EstudianteNoExisteException ex){
        Error error = new Error("Estudiante-no-existe", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<Error>> handlerException(MethodArgumentNotValidException ex){
        List<Error> errores = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errores.add(new Error(fieldError.getField(), fieldError.getDefaultMessage()));
        });
        return ResponseEntity.badRequest().body(errores);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handlerException(Exception ex){
        Error error = new Error("error-desconocido", ex.getMessage());
        return ResponseEntity.internalServerError().body(error);
    }
}
