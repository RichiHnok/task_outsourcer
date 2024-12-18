package com.richi.common.service.task_sample_param_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.richi.common.entity.TaskSampleParam;
import com.richi.common.repository.task_sample_param_rep.TaskSampleParamRepository;

@Service
public class TaskSampleParamServiceImpl implements TaskSampleParamService{

    @Autowired
    private TaskSampleParamRepository taskSampleParamRepository;

    @Override
    public TaskSampleParam getTaskSampleParam(int id) {
        return taskSampleParamRepository.getTaskSampleParam(id);
    }

    @Override
    @Transactional
    public void removeParamFromTaskSample(int id) {
        taskSampleParamRepository.removeParamFromTaskSample(id);
    }
    
}
