package com.theona.anno;

import com.theona.Validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {StateValidation.class})
public @interface State {
    String message() default "state参数只能是已发布或草稿";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
