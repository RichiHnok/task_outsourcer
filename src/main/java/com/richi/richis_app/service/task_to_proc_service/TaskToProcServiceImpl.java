package com.richi.richis_app.service.task_to_proc_service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.richi.richis_app.entity.TaskToProc;
import com.richi.richis_app.repository.task_to_proc_rep.TaskToProcRepository;

@Service
public class TaskToProcServiceImpl implements TaskToProcService{

    @Autowired
    private TaskToProcRepository taskToProcRepository;

    @Override
    @Transactional
    public void deleteTaskToProc(int id) {
        taskToProcRepository.deleteTaskToProc(id);
    }

    @Override
    @Transactional
    public List<TaskToProc> getAllTasksToProc() {
        return taskToProcRepository.getAllTasksToProc();
    }

    @Override
    @Transactional
    public TaskToProc getTaskToProc(int id) {
        return taskToProcRepository.getTaskToProc(id);
    }

    @Override
    @Transactional
    public void saveTaskToProc(TaskToProc taskToProc) {
        taskToProcRepository.saveTaskToProc(taskToProc);
    }
    
}
