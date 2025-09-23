package org.binaryminds.kinalnotes.dominio.exception;

public class CursoYaExisteException extends RuntimeException {
    public CursoYaExisteException(String cursoTitulo) {
        super("El docente: " + cursoTitulo + " ya existe en el sistema.");
    }
}
