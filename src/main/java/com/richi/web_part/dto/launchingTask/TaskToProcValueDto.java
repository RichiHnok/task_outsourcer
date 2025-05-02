package com.richi.web_part.dto.launchingTask;

import com.richi.common.enums.TaskSampleParamType;
import com.richi.web_part.validation.launchingTask.CheckParamInput;

@Deprecated
@CheckParamInput
public record TaskToProcValueDto(
    Integer taskSampleParamId,
    String paramName,
    TaskSampleParamType paramType,
    Object value
){
    
}
