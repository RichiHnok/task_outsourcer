package com.richi.web_part.dto.controlPanel;

import java.util.Date;
import java.util.List;

import com.richi.common.enums.TaskToProcStatus;
import com.richi.web_part.dto.commonDto.TaskParamInfoDto;

public record TaskInfoForControlPanelDto(
    Integer taskId
    , Date startTime
    , String userLogin
    , String taskSampleName
    , List<TaskParamInfoDto> taskParams
    , TaskToProcStatus taskStatus
) {

}
