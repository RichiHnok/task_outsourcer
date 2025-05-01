package com.richi.web_part.validation.common;

import jakarta.validation.ConstraintValidatorContext;

/**
 * Интерфейс для валидаторов для установки сообщения об ошибке
 */
public interface SetErrorMessage {
    /**
     * Установка сообщения об ошибке.
     * @param errorMessage - сообщение об ошибке
     * @param context - объект для доставки сообщения
     */
    default void setErrorMessage(
        String errorMessage
        , ConstraintValidatorContext context
    ){
        context.disableDefaultConstraintViolation();
        context
            .buildConstraintViolationWithTemplate(errorMessage)
            .addConstraintViolation();
    }
}
