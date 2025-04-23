package com.richi.web_part.dto.launchingTask;

import java.util.List;

import jakarta.validation.Valid;

public record LaunchingTaskDto(
    Integer taskSampleId
    , String taskSampleName
    , String taskSampleDescription
    , @Valid List<TaskToProcValueDto> values
){

}
