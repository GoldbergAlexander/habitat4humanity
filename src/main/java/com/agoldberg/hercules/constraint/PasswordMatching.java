package com.agoldberg.hercules.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchingValidator.class)
public @interface PasswordMatching {
    String message() default "Password confirmation does not match";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
