package me.seyrek.library_management_system.copy.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotLoanedValidator.class)
public @interface NotLoaned {
    String message() default "Copy status cannot be LOANED.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
