package com.richi.web_part.dto.taskToProcVal;

import com.richi.common.enums.TaskSampleParamType;

public record TaskToProcValueDto(
    Integer taskSampleParamId
    , String paramName
    , TaskSampleParamType paramType
    , Object value
){
    
}
