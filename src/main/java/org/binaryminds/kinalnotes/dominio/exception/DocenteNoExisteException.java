package org.binaryminds.kinalnotes.dominio.exception;

public class DocenteNoExisteException extends RuntimeException {
    public DocenteNoExisteException(Long codigoDocente) {
        super("El docente con codigo: " + codigoDocente + " no existe en el sistema.");
    }
}
