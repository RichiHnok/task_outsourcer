package com.richi.richis_app.repository;

import com.richi.richis_app.entity.TaskSampleParam;

public interface TaskSampleParamRepository {
    
    public TaskSampleParam getTaskSampleParam(int id);

    public void removeParamFromTaskSample(int id);
}
