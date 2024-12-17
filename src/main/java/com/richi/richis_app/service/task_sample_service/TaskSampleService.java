package com.richi.richis_app.service.task_sample_service;

import java.nio.file.Path;
import java.util.List;

import com.richi.richis_app.entity.TaskSample;

public interface TaskSampleService {
    
    List<TaskSample> getAllTaskSamples();

    void saveTaskSample(TaskSample taskSample);

    TaskSample getTaskSample(int id);

    void deleteTaskSample(int id);
}
