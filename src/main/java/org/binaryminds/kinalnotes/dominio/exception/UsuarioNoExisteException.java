package org.binaryminds.kinalnotes.dominio.exception;

public class UsuarioNoExisteException extends RuntimeException {
    public UsuarioNoExisteException(Long codigoUsuario) {
        super("El usuario con codigo: " + codigoUsuario + " no existe en el sistema.");
    }
}
