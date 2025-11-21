package Grupo_5.UTNC.exception;

/**
 * Excepción lanzada cuando no se encuentra un recurso solicitado
 */
public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    // Constructor para recursos específicos
    public static ResourceNotFoundException forResource(String resourceType, Object id) {
        return new ResourceNotFoundException(
            String.format("%s con ID '%s' no fue encontrado", resourceType, id)
        );
    }
    
    // Constructores específicos para entidades
    public static ResourceNotFoundException forAlumno(String dni) {
        return new ResourceNotFoundException("Alumno con DNI '" + dni + "' no fue encontrado");
    }
    
    public static ResourceNotFoundException forCarrera(Long id) {
        return new ResourceNotFoundException("Carrera con ID '" + id + "' no fue encontrada");
    }
    
    public static ResourceNotFoundException forCurso(Long id) {
        return new ResourceNotFoundException("Curso con ID '" + id + "' no fue encontrado");
    }
    
    public static ResourceNotFoundException forInscripcion(Long id) {
        return new ResourceNotFoundException("Inscripción con ID '" + id + "' no fue encontrada");
    }
}