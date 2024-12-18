package com.richi.common.service.task_sample_service;

import java.nio.file.Path;
import java.util.List;

import com.richi.common.entity.TaskSample;

public interface TaskSampleService {
    
    List<TaskSample> getAllTaskSamples();

    void saveTaskSample(TaskSample taskSample);

    TaskSample getTaskSample(int id);

    void deleteTaskSample(int id);

    String getFolderForStoreScriptFile(int id);
}
