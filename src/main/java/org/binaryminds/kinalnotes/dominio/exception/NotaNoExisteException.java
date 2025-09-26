package org.binaryminds.kinalnotes.dominio.exception;

public class NotaNoExisteException extends RuntimeException {
    public NotaNoExisteException(Long codigoNota) {
        super("La nota con codigo: " + codigoNota + " no existe en el sistema.");
    }
}
