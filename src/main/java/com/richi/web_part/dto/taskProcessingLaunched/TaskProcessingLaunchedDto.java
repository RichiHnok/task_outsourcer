package com.richi.web_part.dto.taskProcessingLaunched;

import java.util.List;

public record TaskProcessingLaunchedDto(
    String taskSampleName
    , List<TaskParamWithValueDto> taskValues
) {
    
}
