package com.richi.web_part.validation.launchingTask;

import org.springframework.web.multipart.MultipartFile;

import com.richi.common.entity.taskSampleParam.TaskSampleParam;
import com.richi.common.entity.taskSampleParam.subTypes.TaskSampleIntegerParam;
import com.richi.common.entity.taskSampleParam.subTypes.TaskSampleStringParam;
import com.richi.common.service.TaskSampleParamService;
import com.richi.web_part.dto.launchingTask.paramInfo.ParamValueInfo;
import com.richi.web_part.validation.common.SetErrorMessage;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Валидатор параметров, необходимых для запуска выполнения задачи.<p>
 * Реализована проверка INTEGER, STRING типов
 */

public class ParamInputValidator implements
    ConstraintValidator<CheckParamInput, ParamValueInfo>
    , SetErrorMessage
{

    /** 
     * Сервис для получения дополнительной информации о параметре для
     * его проверки.
     */
    private final TaskSampleParamService taskSampleParamService;

    public ParamInputValidator(
        TaskSampleParamService taskSampleParamService
    ) {
        this.taskSampleParamService = taskSampleParamService;
    }

    /**
     * Проверка, что введено корректное значение. Онтосительно типа параметра
     * определяет способ его проверки.
     * @param paramInfo - dto, в котором находится необходимая информация для
     * запуска выполнения задачи.
     * @param context - сюда помещается сообщение об ошибке
     * @return {@code false} если не значение не прошло одну из проверок.
     */
    @Override
    public boolean isValid(ParamValueInfo paramInfo, ConstraintValidatorContext context) {

        TaskSampleParam sampleParamInfo = taskSampleParamService.getTaskSampleParam(paramInfo.taskSampleParamId());

        switch (sampleParamInfo.getType()) {
            case INTEGER:
                return validateIntegerType(paramInfo.value(), sampleParamInfo, context);
            case STRING:
                return validateStringType(paramInfo.value(), sampleParamInfo, context);
            case FILE:
                return validateFileType(paramInfo.value(), sampleParamInfo, context);
            default:
                context.disableDefaultConstraintViolation();
                context
                    .buildConstraintViolationWithTemplate("We can't validate this value, because its type is not supported")
                    .addConstraintViolation();
                return false;
        }
    }
    
    /**
     * Проверка корректности введённого значения типа INTEGER относительно
     * заданых ограничений.<p>
     * Проверяется следующее:<p>
     * - Что значение не пустое и не содержит только пробельные символы;<p>
     * - Что значение является целым числом;
     * - Что значение, находится в промежутке [min;max].
     * @param value - проверяемое значение в виде Object. Преобразуется в
     * Integer.
     * @param paramInfo - полное описание параметра типа INTEGER
     * @param context - сюда помещается сообщение об ошибке
     * @return {@code false} если не значение не прошло одну из проверок.
     */
    private boolean validateIntegerType(Object value, TaskSampleParam paramInfo, ConstraintValidatorContext context){
        var intParamInfo = (TaskSampleIntegerParam) paramInfo;

        String strForm = (String) value;
        if(strForm.isEmpty() || strForm.isBlank()){
            setErrorMessage(getErrorMessageValueCannotBeEmpty(), context);
            return false;
        }

        try {
            Integer intForm = Integer.parseInt(strForm);
            if(intForm < intParamInfo.getMin() || intForm > intParamInfo.getMax()){
                setErrorMessage(getErrorMessageIntegerNotInAllowedInterval(), context);
                return false;
            }

        } catch (NumberFormatException e) {
            setErrorMessage(getErrorMessageInvalidIntegerValue(), context);
            return false;
        }
        return true;
    }

    /**
     * Проверка корректности введённого значения типа STRING относительно
     * заданых ограничений.<p>
     * Проверяется следующее:<p>
     * - Что значение не пустое и не содержит только пробельные символы;<p>
     * - Что значение удовлетворяет регулярному выражению для данного
     * параметра.
     * @param value - проверяемое значение в виде Object. Преобразуется в
     * String.
     * @param paramInfo - полное описание параметра типа STRING
     * @param context - сюда помещается сообщение об ошибке
     * @return {@code false} если не значение не прошло одну из проверок.
     */
    private boolean validateStringType(Object value, TaskSampleParam paramInfo, ConstraintValidatorContext context){
        var strParamInfo = (TaskSampleStringParam) paramInfo;

        String strValue = (String) value;

        if(strValue.isEmpty() || strValue.isBlank()){
            setErrorMessage(getErrorMessageValueCannotBeEmpty(), context);
            return false;
        }
        if(!strValue.matches(strParamInfo.getRegExConstraint())){
            setErrorMessage(getErrorMessageValueDoesntMatchRegex(), context);
            return false;
        }
        return true;
    }

    /**
     * TODO
     * Проверка корректности приложенного файла относительно
     * заданых ограничений.<p>
     * Проверяется следующее:<p>
     * - Что пользователь приложил файл и размер файла не -1
     * параметра.
     * @param value - проверяемое значение в виде Object. Преобразуется в
     * MultipartFile.
     * @param paramInfo - полное описание параметра типа FILE
     * @param context - сюда помещается сообщение об ошибке
     * @return
     */
    private boolean validateFileType(Object value, TaskSampleParam paramInfo, ConstraintValidatorContext context){
        MultipartFile fileFormat = (MultipartFile) value;
        if(fileFormat.getSize() == 0){
            setErrorMessage(getErrorMessageNoFile(), context);
            return false;
        }
        return true;
    }

    /**
     * Получение сообщения о том, что пользователь не заполнил поле, на
     * нужном языке относительно локали пользователя.
     * @return {@code errorMessage} - локализованное сообщение об ошибке
     */
    private String getErrorMessageValueCannotBeEmpty(){
        // TODO сообщение с нужным языком должно браться из отдельного файла
        String errorMessage = "Данное поле обязательно для заполнения";
        return errorMessage;
    }

    /**
     * Получение сообщения о том, что пользователь неправильно ввёл
     * целочисленное значение, на нужном языке относительно локали
     * пользователя.
     * @return {@code errorMessage} - локализованное сообщение об ошибке
     */
    private String getErrorMessageInvalidIntegerValue(){
        // TODO сообщение с нужным языком должно браться из отдельного файла
        String errorMessage = "Значение должно быть целым числом";
        return errorMessage;
    }

    /**
     * Получение сообщения о том, что пользователь указал значение из
     * недопустимого промежутка, на нужном языке относительно локали
     * пользователя.
     * @return {@code errorMessage} - локализованное сообщение об ошибке
     */
    private String getErrorMessageIntegerNotInAllowedInterval(){
        // TODO сообщение с нужным языком должно браться из отдельного файла
        String errorMessage = "Значение находится за пределами допустимых значений";
        return errorMessage;
    }

    /**
     * Получение сообщения о том, что введённое значение не удовлетворяет
     * регулярному выражению, на нужном языке относительно локали
     * пользователя.
     * @return {@code errorMessage} - локализованное сообщение об ошибке
     */
    private String getErrorMessageValueDoesntMatchRegex(){
        // TODO сообщение с нужным языком должно браться из отдельного файла
        String errorMessage = "Значение неправильного формата";
        return errorMessage;
    }

    /**
     * Получение сообщения о том, что пользователь не приложил никакого файла
     * , на нужном языке относительно локали
     * пользователя.
     * @return {@code errorMessage} - локализованное сообщение об ошибке
     */
    private String getErrorMessageNoFile(){
        // TODO сообщение с нужным языком должно браться из отдельного файла
        String errorMessage = "Необходимо приложить файл";
        return errorMessage;
    }
}
