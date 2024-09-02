package com.richi.richis_app.repository.task_sample_param_rep;

import com.richi.richis_app.entity.TaskSampleParam;

public interface TaskSampleParamRepository {
    
    public TaskSampleParam getTaskSampleParam(int id);

    public void removeParamFromTaskSample(int id);
}
