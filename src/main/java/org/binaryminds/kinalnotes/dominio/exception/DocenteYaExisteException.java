package org.binaryminds.kinalnotes.dominio.exception;

public class DocenteYaExisteException extends RuntimeException {
    public DocenteYaExisteException(String docenteTitulo) {
        super("El docente: " + docenteTitulo + " ya existe en el sistema.");
    }
}
