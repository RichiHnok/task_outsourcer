package com.richi.web_part.validation.launchingTask;

import com.richi.common.entity.taskSampleParam.TaskSampleParam;
import com.richi.common.entity.taskSampleParam.subTypes.TaskSampleIntegerParam;
import com.richi.common.entity.taskSampleParam.subTypes.TaskSampleStringParam;
import com.richi.common.service.TaskSampleParamService;
import com.richi.web_part.dto.launchingTask.TaskToProcValueDto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CheckTaskParamValidator implements ConstraintValidator<CheckTaskParam, TaskToProcValueDto>{

    private final TaskSampleParamService taskSampleParamService;

    public CheckTaskParamValidator(
        TaskSampleParamService taskSampleParamService
    ) {
        this.taskSampleParamService = taskSampleParamService;
    }

    @Override
    public boolean isValid(TaskToProcValueDto taskDto, ConstraintValidatorContext context) {

        TaskSampleParam paramInfo = taskSampleParamService.getTaskSampleParam(taskDto.taskSampleParamId());

        switch (taskDto.paramType()) {
            case INTEGER:
                return validateIntegerType(taskDto.value(), paramInfo, context);
            case STRING:
                return validateStringType(taskDto.value(), paramInfo, context);
            case FILE:
                return validateFileType(taskDto.value(), paramInfo, context);
            default:
                context.disableDefaultConstraintViolation();
                context
                    .buildConstraintViolationWithTemplate("We can't validate this value, because its type is not supported")
                    .addConstraintViolation();
                return false;
        }
    }
    
    private boolean validateIntegerType(Object value, TaskSampleParam paramInfo, ConstraintValidatorContext context){
        String errorMessage = "error message";
        var intParamInfo = (TaskSampleIntegerParam) paramInfo;

        String strForm = (String) value;
        if(strForm.isEmpty() || strForm.isBlank()){
            errorMessage = "this value should not be empty or blank";
            context.disableDefaultConstraintViolation();
            context
                .buildConstraintViolationWithTemplate(errorMessage)
                .addConstraintViolation();
            return false;
        }

        try {
            Integer intForm = Integer.parseInt(strForm);
            if(intForm >= intParamInfo.getMin() && intForm <= intParamInfo.getMax()){
                return true;
            }else{
                errorMessage = "this value must be in range " + intParamInfo.getMin() + " - " + intParamInfo.getMax();
            }

        } catch (NumberFormatException e) {
            errorMessage = "'" + strForm +"' is not an integer";
        }
        context.disableDefaultConstraintViolation();
        context
            .buildConstraintViolationWithTemplate(errorMessage)
            .addConstraintViolation();
        return false;
    }

    private boolean validateStringType(Object value, TaskSampleParam paramInfo, ConstraintValidatorContext context){
        String errorMessage = "error message";
        var strParamInfo = (TaskSampleStringParam) paramInfo;

        String strValue = (String) value;

        if(strValue.isEmpty() || strValue.isBlank()){
            errorMessage = "this value should not be empty or blank";
            context.disableDefaultConstraintViolation();
            context
                .buildConstraintViolationWithTemplate(errorMessage)
                .addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean validateFileType(Object value, TaskSampleParam paramInfo, ConstraintValidatorContext context){
        return false;
    }
}
