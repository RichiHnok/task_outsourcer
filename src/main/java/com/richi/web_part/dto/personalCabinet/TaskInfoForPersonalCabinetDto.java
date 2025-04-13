package com.richi.web_part.dto.personalCabinet;

import java.util.Date;
import java.util.List;

import com.richi.common.enums.TaskToProcStatus;

public record TaskInfoForPersonalCabinetDto(
    Integer taskId
    , Date startTime
    , String taskSampleName
    , TaskToProcStatus taskStatus
    , Boolean isFinished
    , List<TaskParamInfoDto> taskParams
) {
    
}
