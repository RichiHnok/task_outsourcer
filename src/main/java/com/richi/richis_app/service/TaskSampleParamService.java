package com.richi.richis_app.service;

import com.richi.richis_app.entity.TaskSampleParam;

public interface TaskSampleParamService {
    
    public TaskSampleParam getTaskSampleParam(int id);
    
    public void removeParamFromTaskSample(int id);
}
