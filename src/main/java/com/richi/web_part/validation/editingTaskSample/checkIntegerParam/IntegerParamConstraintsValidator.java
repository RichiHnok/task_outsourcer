package com.richi.web_part.validation.editingTaskSample.checkIntegerParam;

import java.math.BigInteger;

import com.richi.web_part.dto.editingTaskSample.taskSampleParam.typeConstraints.IntegerParamConstraintsDto;
import com.richi.web_part.validation.common.SetErrorMessage;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Валидатор введёных ограничений для параметра типа
 * {@link com.richi.common.enums.TaskSampleParamType#INTEGER INTEGER}
 * в редакторе шаблона задачи
 */

public class IntegerParamConstraintsValidator
implements ConstraintValidator<CheckIntegerParamConstraints, IntegerParamConstraintsDto>
    , SetErrorMessage     
{
    
    /**
     * Проверка, что промежуток допустимых значений задан корректоно.<p>
     * Проверяется следующее:<p>
     * - Что минимальное допустимое значение имеет корректную форму целого числа;<p>
     * - Что минимальное допустимое значение не выходит за пределы допустимых значений
     * типв Long;<p>
     * - Что максимальное допустимое значение имеет корректную форму целого числа;<p>
     * - Что максимальное допустимое значение не выходит за пределы допустимых значений
     * типв Long;<p>
     * - Что минимальное допустимое значение не больше или равно максимальному
     * @param value - dto, в котором находится ограничение на промежуток допустимых значений
     * @param context - в этой штуке будет помещаться сообщение об ошибке
     * @return {@code false} если не значение не прошло одну из проверок.
     */
    @Override
    public boolean isValid(IntegerParamConstraintsDto value, ConstraintValidatorContext context) {
        Long min = null;
        Long max = null;
        try {
            min = Long.valueOf(value.min());
        } catch (NumberFormatException e) {
            if (isOutOfLongBounds(value.min())) {
                setErrorMessage(getErrorMessageMinOutOfBounds(), context);
            }else{
                setErrorMessage(getErrorMessageInvalidMinConstraint(), context);
            }
            return false;
        }
        try {
            max = Long.valueOf(value.max());
        } catch (NumberFormatException e) {
            if (isOutOfLongBounds(value.max())) {
                setErrorMessage(getErrorMessageMaxOutOfBounds(), context);
            }else{
                setErrorMessage(getErrorMessageInvalidMaxConstraint(), context);
            }
            return false;
        }
        if(min >= max){
            setErrorMessage(getErrorMessageIncorrectInterval(), context);
            return false;
        }
        return true;
    }

    /**
     * Метод проверяет, находится ли строковое представление целого числа в
     * пределах допустимых значений типа Long. Нужен чтобы уточнить причину
     * невозможности преобразования строкового значения числа в тип Long. 
     * @param value - проверяемое значение
     * @return {@code true} - значение можно преобразовать в целое число, но оно выходит за
     * область допустимых значений типа Long;<p>
     * {@code false} - значение нельзя преобразовать в целое число.
     */
    private boolean isOutOfLongBounds(String value){
        try {
            new BigInteger(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Получение сообщения о том, что минимальное допустимое значение выходит за область
     * допустимых значений типа Long, на нужном языке относительно локали пользователя
     * @return {@code errorMessage} - локализованное сообщение об ошибке
     */
    private String getErrorMessageMinOutOfBounds(){
        // TODO сообщение с нужным языком должно браться из отдельного файла
        String errorMessage = "Минимальное ограничение должно быть в пределах ±" + Long.MAX_VALUE;
        return errorMessage;
    }

    /**
     * Получение сообщения о том, что максимальное допустимое значение выходит за область
     * допустимых значений типа Long, на нужном языке относительно локали пользователя
     * @return {@code errorMessage} - локализованное сообщение об ошибке
     */
    private String getErrorMessageMaxOutOfBounds(){
        // TODO сообщение с нужным языком должно браться из отдельного файла
        String errorMessage = "Максимальное ограничение должно быть в пределах ±" + Long.MAX_VALUE;
        return errorMessage;
    }

    /**
     * Получение сообщения о том, что минимальное допустимое значение должно иметь формат
     * целого числа, на нужном языке относительно локали пользователя
     * @return {@code errorMessage} - локализованное сообщение об ошибке
     */
    private String getErrorMessageInvalidMinConstraint(){
        // TODO сообщение с нужным языком должно браться из отдельного файла
        String errorMessage = "Минимальное ограничение должно быть целым числом";
        return errorMessage;
    }

    /**
     * Получение сообщения о том, что максимальное допустимое значение должно иметь формат
     * целого числа, на нужном языке относительно локали пользователя
     * @return {@code errorMessage} - локализованное сообщение об ошибке
     */
    private String getErrorMessageInvalidMaxConstraint(){
        // TODO сообщение с нужным языком должно браться из отдельного файла
        String errorMessage = "Максимальное ограничение должно быть целым числом";
        return errorMessage;
    }

    /**
     * Получение сообщения о том, что минимальное значение должно быть меньше максимального,
     * на нужном языке относительно локали пользователя
     * @return {@code errorMessage} - локализованное сообщение об ошибке
     */
    private String getErrorMessageIncorrectInterval(){
        // TODO сообщение с нужным языком должно браться из отдельного файла
        String errorMessageTemplate = "Минимальное ограничение должно быть меньше максимального";
        return errorMessageTemplate;
    }
}
