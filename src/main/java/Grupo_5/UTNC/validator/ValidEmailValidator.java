package Grupo_5.UTNC.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;

public class ValidEmailValidator implements ConstraintValidator<ValidEmail, String> {
    private final EmailValidator delegate = new EmailValidator();

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return delegate.isValid(value, context);
    }
}
