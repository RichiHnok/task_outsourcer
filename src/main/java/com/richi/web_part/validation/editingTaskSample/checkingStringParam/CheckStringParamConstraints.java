package com.richi.web_part.validation.editingTaskSample.checkingStringParam;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Специальная аннотация для валидации объекта
 * {@link com.richi.web_part.dto.editingTaskSample.taskSampleParam.typeConstraints.StringParamConstraintsDto
 * StringParamConstraintsDto}. Инициирует проверку методом
 * {@link StringParamConstraintsValidator#isValid(StringParamConstraintsDto, jakarta.validation.ConstraintValidatorContext)
 * StringConstraintsVaidator.isValid(...)}
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = StringParamConstraintsValidator.class)
public @interface CheckStringParamConstraints {
    
    String message() default "wrong input";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
