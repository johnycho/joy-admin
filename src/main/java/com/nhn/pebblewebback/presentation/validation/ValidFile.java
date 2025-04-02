package com.nhn.pebblewebback.presentation.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidFileAop.class)
public @interface ValidFile {
  String message() default "Invalid file";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
