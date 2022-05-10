package mk.finki.lm.customValidation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({ElementType.TYPE,ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "Invalid Password! Password must contain at least 1 Uppercase, 1 Lowercase, 1 Digit and 1 Special Character ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
