package Grupo_5.UTNC.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = ValidEmailValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface ValidEmail {
    String message() default "Email inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
