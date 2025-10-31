package Grupo_5.UTNC.rest;

import Grupo_5.UTNC.dto.InscripcionDTO;
import Grupo_5.UTNC.service.InscripcionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/inscripciones")
public class InscripcionRestController {

    private final InscripcionService inscripcionService;

    @Autowired
    public InscripcionRestController(InscripcionService inscripcionService) {
        this.inscripcionService = inscripcionService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> recibirInscripcion(@Valid @RequestBody InscripcionDTO inscripcionDTO) {
        inscripcionService.registrarInscripcion(inscripcionDTO);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Inscripción recibida exitosamente.");

        return ResponseEntity.ok(response);
    }

    // Manejador de excepciones para validación
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
