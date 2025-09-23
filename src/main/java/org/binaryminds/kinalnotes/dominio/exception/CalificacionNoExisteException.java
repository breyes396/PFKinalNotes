package org.binaryminds.kinalnotes.dominio.exception;

public class CalificacionNoExisteException extends RuntimeException {
    public CalificacionNoExisteException(Integer calificacion) {
        super("No existe una nota con punteo: " + calificacion);
    }
}
