package com.richi.richis_app.service.task_sample_service;

import java.util.List;

import com.richi.richis_app.entity.TaskSample;

public interface TaskSampleService {
    
    public List<TaskSample> getAllTaskSamples();

    public void saveTaskSample(TaskSample taskSample);

    public TaskSample getTaskSample(int id);

    public void deleteTaskSample(int id);
}
