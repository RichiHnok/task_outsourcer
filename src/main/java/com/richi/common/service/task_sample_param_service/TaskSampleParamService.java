package com.richi.common.service.task_sample_param_service;

import com.richi.common.entity.TaskSampleParam;

public interface TaskSampleParamService {
    
    public TaskSampleParam getTaskSampleParam(int id);
    
    public void removeParamFromTaskSample(int id);
}
