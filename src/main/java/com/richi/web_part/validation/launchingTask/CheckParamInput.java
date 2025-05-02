package com.richi.web_part.validation.launchingTask;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.richi.web_part.dto.launchingTask.paramInfo.ParamValueInfo;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Специальная аннотация для валидации объекта
 * {@link com.richi.web_part.dto.launchingTask.paramInfo.ParamValueInfo ParamValueInfo}
 * . Инициирует проверку методом
 * {@link ParamInputValidator#isValid(ParamValueInfo, jakarta.validation.ConstraintValidatorContext)
 * ParamInputValidator.isValid(...)}
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ParamInputValidator.class)
public @interface CheckParamInput {
    
    String message() default "wrong input";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
