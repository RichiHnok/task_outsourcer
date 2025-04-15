package com.richi.web_part.dto.taskProcessingLaunched;

import com.richi.common.enums.TaskSampleParamType;

public record TaskParamWithValueDto(
    Integer taskId
    , String paramName
    , TaskSampleParamType paramType
    , String value
) {
    
}
