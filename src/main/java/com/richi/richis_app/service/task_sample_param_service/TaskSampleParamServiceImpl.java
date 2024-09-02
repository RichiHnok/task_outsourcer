package com.richi.richis_app.service.task_sample_param_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.richi.richis_app.entity.TaskSampleParam;
import com.richi.richis_app.repository.task_sample_param_rep.TaskSampleParamRepository;

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
