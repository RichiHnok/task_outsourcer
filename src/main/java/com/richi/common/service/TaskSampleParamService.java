package com.richi.common.service;

import org.springframework.stereotype.Service;

import com.richi.common.entity.taskSampleParam.TaskSampleParam;
import com.richi.common.repository.TaskSampleParamRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TaskSampleParamService {
    
    private final TaskSampleParamRepository taskSampleParamRepository;

    public TaskSampleParamService(TaskSampleParamRepository taskSampleParamRepository) {
        this.taskSampleParamRepository = taskSampleParamRepository;
    }

    public TaskSampleParam getTaskSampleParam(Integer taskSampleParamId) {
        return taskSampleParamRepository.findById(taskSampleParamId).orElseThrow(
            () -> new EntityNotFoundException("There is no task sample with ID:: " + taskSampleParamId)
        );
    }

    public void removeParamFromTaskSample(Integer taskSampleParamId) {
        taskSampleParamRepository.deleteById(taskSampleParamId);
    }
}
