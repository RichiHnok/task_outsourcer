package com.richi.richis_app.repository.task_sample_rep;

import java.util.List;

import com.richi.richis_app.entity.TaskSample;

public interface TaskSampleRepository {
    
    public List<TaskSample> getAllTaskSamples();

    public void saveTaskSample(TaskSample taskSample);

    public TaskSample getTaskSample(int id);

    public void deleteTaskSample(int id);
}
