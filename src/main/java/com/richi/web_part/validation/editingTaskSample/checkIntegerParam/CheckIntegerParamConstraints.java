package com.richi.web_part.validation.editingTaskSample.checkIntegerParam;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Специальная аннотация для валидации объекта {@link com.richi.web_part.dto.editingTaskSample.taskSampleParam.typeConstraints.IntegerParamConstraintsDto
 * IntegerParamConstraintsDto}. Инициирует проверку методом
 * {@link com.richi.web_part.validation.editingTaskSample.checkIntegerParam.IntegerParamValidator#isValid(com.richi.web_part.dto.editingTaskSample.taskSampleParam.typeConstraints.IntegerParamConstraintsDto, jakarta.validation.ConstraintValidatorContext) IntegerConstraintsVaidator.isValid()}
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = IntegerParamValidator.class)
public @interface CheckIntegerParamConstraints {
    
    String message() default "wrong input";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
