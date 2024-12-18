package com.richi.common.service.service1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

// import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.richi.common.entity.TaskSample;
import com.richi.common.repository.task_sample_rep.TaskSampleRepository;

@Service
public class Service1Impl implements Service1{

    @Autowired
    private TaskSampleRepository taskSampleRepository;

    @Override
    public void launchProgram() {
        // System.out.println("Launching program");
        // Runtime runtime = Runtime.getRuntime();
        // String[] cmdarray = {"start", "calc"};
        // try {
        //     runtime.exec("cmd /c start \"\" start calc");
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
    }

    @Override
    @Transactional
    public List<TaskSample> getAllTaskSamples() {
        return taskSampleRepository.getAllTaskSamples();
    }

    @Override
    @Transactional
    public void saveTaskSample(TaskSample taskSample) {
        taskSampleRepository.saveTaskSample(taskSample);
    }
    
    public void test(){
        System.out.println("it works");
    }
}
