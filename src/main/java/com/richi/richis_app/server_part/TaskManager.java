package com.richi.richis_app.server_part;

import java.util.LinkedList;

import com.richi.richis_app.entity.TaskToProc;
import com.richi.richis_app.entity.TaskSample;
import com.richi.richis_app.entity.TaskValues;
import com.richi.richis_app.entity.User;

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
