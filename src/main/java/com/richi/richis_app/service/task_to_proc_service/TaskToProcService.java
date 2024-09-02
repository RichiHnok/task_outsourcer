package com.richi.richis_app.service.task_to_proc_service;

import java.util.List;

import com.richi.richis_app.entity.TaskToProc;

public interface TaskToProcService {
    
    public List<TaskToProc> getAllTasksToProc();

    public void saveTaskToProc(TaskToProc taskToProc);

    public TaskToProc getTaskToProc(int id);

    public void deleteTaskToProc(int id);
}
