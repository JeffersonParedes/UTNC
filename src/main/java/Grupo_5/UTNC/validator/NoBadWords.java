package Grupo_5.UTNC.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD}) // Esta anotación se podrá usar en campos de una clase
@Retention(RetentionPolicy.RUNTIME) // Se ejecutará en tiempo de ejecución
@Constraint(validatedBy = NoBadWordsValidator.class) // La lógica de validación estará en la clase NoBadWordsValidator
public @interface NoBadWords {
    // Mensaje de error por defecto
    String message() default "El nombre contiene palabras no permitidas.";

    // Boilerplate requerido por la especificación de validación
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
