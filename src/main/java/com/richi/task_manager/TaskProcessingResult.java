package com.richi.task_manager;

import com.richi.common.entity.TaskToProc;

public record TaskProcessingResult(
    TaskToProc task,
    String endType
) {
    
}
