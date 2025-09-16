package org.binaryminds.kinalnotes.dominio.exception;

public class UsuarioYaExisteException extends RuntimeException {
    public UsuarioYaExisteException(String usuarioTitulo) {
        super("El usuario: " + usuarioTitulo + " ya existe en el sistema.");
    }
}
