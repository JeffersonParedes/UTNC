package Grupo_5.UTNC.exception;

/**
 * Excepción lanzada cuando se intenta crear un usuario que ya existe
 */
public class UserAlreadyExistsException extends RuntimeException {
    
    public UserAlreadyExistsException(String message) {
        super(message);
    }
    
    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
    
    // Constructor específico para usuario duplicado por email
    public static UserAlreadyExistsException forEmail(String email) {
        return new UserAlreadyExistsException("Ya existe un usuario registrado con el email: " + email);
    }
    
    // Constructor específico para usuario duplicado por DNI
    public static UserAlreadyExistsException forDni(String dni) {
        return new UserAlreadyExistsException("Ya existe un usuario registrado con el DNI: " + dni);
    }
}