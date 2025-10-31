package Grupo_5.UTNC.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class NoBadWordsValidator implements ConstraintValidator<NoBadWords, String> {

    // Lista de palabras no permitidas
    private final List<String> badWords = Arrays.asList("test", "prueba", "admin");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            // Dejamos que @NotEmpty se encargue de los valores nulos o vacíos
            return true;
        }

        // Comprobamos si el valor (en minúsculas) está en nuestra lista de palabras no permitidas
        return badWords.stream().noneMatch(value.toLowerCase()::contains);
    }
}
