package com.richi.web_part.dto.taskToProcVal;

import java.util.List;

public record LaunchingTaskDto(
    Integer taskSampleId
    , String taskSampleName
    , String taskSampleDescription
    , List<TaskToProcValueDto> values
){

}
