package com.richi.common.service.service1;

import java.util.List;

import com.richi.common.entity.TaskSample;

public interface Service1 {
    
    public void launchProgram();

    public List<TaskSample> getAllTaskSamples();

    public void saveTaskSample(TaskSample taskSample);

    public void test();
}
