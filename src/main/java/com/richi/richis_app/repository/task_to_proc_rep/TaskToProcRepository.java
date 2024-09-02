package com.richi.richis_app.repository.task_to_proc_rep;

import java.util.List;

import com.richi.richis_app.entity.TaskToProc;

public interface TaskToProcRepository {
    
    public List<TaskToProc> getAllTasksToProc();

    public void saveTaskToProc(TaskToProc taskToProc);

    public TaskToProc getTaskToProc(int id);

    public void deleteTaskToProc(int id);
}
