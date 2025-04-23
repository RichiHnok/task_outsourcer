package com.richi.web_part.dto.editingTaskSample.taskSampleParam;

import com.richi.common.enums.TaskSampleParamType;

public record TaskSampleParamMainInfoDto (
    Integer id,
    String name,
    String uuid,
    TaskSampleParamType type
){
    
}
