package Grupo_5.UTNC.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidDNIValidator implements ConstraintValidator<ValidDNI, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return value.matches("\\d{8}");
    }
}
