package com.richi.task_manager;

import java.util.LinkedList;

import com.richi.common.entity.TaskSample;
import com.richi.common.entity.TaskToProc;
import com.richi.common.entity.TaskValues;
import com.richi.common.entity.User;

public class TaskManager extends Thread{
    
    private LinkedList<TaskToProc> tasksToProc;

    public TaskManager(){
        super("TaskManager");
    }

    public void run(){

    }

    public void launchTaskProcessing(){

    }

    public void addTaskToQuee(TaskSample taskSample, TaskValues values, User user){
        
    }
}
