package com.richi.web_part.validation.launchingTask;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CheckTaskParamValidator.class)
public @interface CheckTaskParam {
    
    String message() default "wrong input";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
