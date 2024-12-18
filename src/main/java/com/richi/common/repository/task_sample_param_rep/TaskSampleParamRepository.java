package com.richi.common.repository.task_sample_param_rep;

import com.richi.common.entity.TaskSampleParam;

public interface TaskSampleParamRepository {
    
    public TaskSampleParam getTaskSampleParam(int id);

    public void removeParamFromTaskSample(int id);
}
