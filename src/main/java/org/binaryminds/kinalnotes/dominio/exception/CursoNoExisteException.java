package org.binaryminds.kinalnotes.dominio.exception;

public class CursoNoExisteException extends RuntimeException {
    public CursoNoExisteException(Long codigoCurso) {
        super("El curso con codigo: " + codigoCurso + " no existe en el sistema.");
    }
}
