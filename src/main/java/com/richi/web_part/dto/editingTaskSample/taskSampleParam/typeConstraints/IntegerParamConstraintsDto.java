package com.richi.web_part.dto.editingTaskSample.taskSampleParam.typeConstraints;

public record IntegerParamConstraintsDto(
    String uuid
    , Long min
    , Long max
){

}