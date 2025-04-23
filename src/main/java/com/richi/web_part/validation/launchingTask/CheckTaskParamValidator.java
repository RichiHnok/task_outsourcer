package com.richi.web_part.validation.launchingTask;

import com.richi.web_part.dto.launchingTask.TaskToProcValueDto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CheckTaskParamValidator implements ConstraintValidator<CheckTaskParam, TaskToProcValueDto>{

    @Override
    public boolean isValid(TaskToProcValueDto taskDto, ConstraintValidatorContext context) {

        switch (taskDto.paramType()) {
            case INTEGER:
                return validateIntegerType(taskDto.value(), context);
            case STRING:
                return validateStringType(taskDto.value(), context);
            case FILE:
                return validateFileType(taskDto.value(), context);
            default:
                context.disableDefaultConstraintViolation();
                context
                    .buildConstraintViolationWithTemplate("We can't validate this value, because its type is not supported")
                    .addConstraintViolation();
                return false;
        }
    }
    
    private boolean validateIntegerType(Object value, ConstraintValidatorContext context){
        String errorMessage = "error message";

        String strForm = (String) value;
        try {
            Integer intForm = Integer.parseInt(strForm);
        } catch (NumberFormatException e) {
            errorMessage = "'" + strForm +"' is not an integer";
        }
        context.disableDefaultConstraintViolation();
        context
            .buildConstraintViolationWithTemplate(errorMessage)
            .addConstraintViolation();
        return false;
    }

    private boolean validateStringType(Object value, ConstraintValidatorContext context){
        String strValue = (String) value;
        if(strValue.isEmpty() || strValue.isBlank()){

        }
        return false;
    }

    private boolean validateFileType(Object value, ConstraintValidatorContext context){
        return false;
    }
}
