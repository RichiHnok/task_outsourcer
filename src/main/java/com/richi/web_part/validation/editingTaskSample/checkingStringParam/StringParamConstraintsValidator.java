package com.richi.web_part.validation.editingTaskSample.checkingStringParam;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.richi.web_part.dto.editingTaskSample.taskSampleParam.typeConstraints.StringParamConstraintsDto;
import com.richi.web_part.validation.common.SetErrorMessage;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Валидатор введёных ограничений для параметра типа
 * {@link com.richi.common.enums.TaskSampleParamType#STRING STRING}
 * в редакторе шаблона задачи
 */

public class StringParamConstraintsValidator
implements ConstraintValidator<CheckStringParamConstraints, StringParamConstraintsDto>
    , SetErrorMessage
{
    /**
     * Проверка, что регулярное выражение с подсказкой задано корректно.<p>
     * Проверяется следующее:<p>
     * - Что значение для регулярного выражения - это действительно
     * регулярное выражение
     * - Что подсказа удовлетворяет регулярному выражению
     * @param value - dto, в котором находится ограничение
     * @param context - в этой штуке будет помещаться сообщение об ошибке
     * @return {@code false} если не значение не прошло одну из проверок.
     */
    @Override
    public boolean isValid(StringParamConstraintsDto value, ConstraintValidatorContext context) {
        String regEx = value.regExConstraint();
        String hint = value.hintValue();

        try {
            Pattern.compile(regEx);
        } catch (PatternSyntaxException e) {
            setErrorMessage(getErrorMessageInvalidRegex(), context);
            return false;
        }

        if (!hint.matches(regEx)) {
            setErrorMessage(getErrorMessageHintDoesntMatchesRegex(), context);
            return false;
        }
        return true;
    }

    /**
     * Получение сообщения о том, что значение не является регулярным
     * выражением, на нужном языке относительно локали пользователя
     * @return {@code errorMessage} - локализованное сообщение об ошибке
     */
    private String getErrorMessageInvalidRegex(){
        String errorMessage = "Неверное регулярное выражение";
        return errorMessage;
    }

    /**
     * Получение сообщения о том, что подсказка не удовлетворяет регулярному
     * выражению, на нужном языке относительно локали пользователя
     * @return {@code errorMessage} - локализованное сообщение об ошибке
     */
    private String getErrorMessageHintDoesntMatchesRegex(){
        String errorMessage = "Подсказка не удовлетворяет регулярному выражению";
        return errorMessage;
    }
}
