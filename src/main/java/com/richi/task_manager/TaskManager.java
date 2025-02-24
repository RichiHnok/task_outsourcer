package com.richi.task_manager;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.richi.common.entity.TaskSample;
import com.richi.common.entity.TaskToProc;
import com.richi.common.entity.TaskValues;
import com.richi.common.entity.User;
import com.richi.common.enums.TaskToProcStatus;
import com.richi.common.service.TaskSampleService;
import com.richi.common.service.TaskToProcFilesService;
import com.richi.common.service.TaskToProcService;
import com.richi.common.service.ZipService;

@Service
public class TaskManager extends Thread{

    @Autowired private TaskToProcService taskToProcService;
    @Autowired private TaskToProcFilesService taskToProcFilesService;
    @Autowired private TaskSampleService taskSampleService;
    @Autowired private ZipService zipService;

    public TaskManager(){
        super("TaskManager");
    }

    public void run(){

    }

    public void launchTaskProcessing(){

    }

    public void addTaskToQuee(TaskSample taskSample, TaskValues values, User user){
        
    }

    //TODO должен быть приватным методом
    public void doTask(TaskToProc task) throws Exception{
        String scriptPath = task.getTaskSample().getScriptFilePath().toString();
        ProcessBuilder processBuilder = new ProcessBuilder(
            "python"
            , scriptPath
            , taskToProcFilesService.getOutputFolderForTask(task).toString()
        ).inheritIO();

        Process demoProcess = processBuilder.start();

        taskToProcService.updateTaskStatus(task, TaskToProcStatus.IN_PROCESSING);

        demoProcess.waitFor();

        //TODO Надо как-то сообразить передачу сообщений о результате выполнения из скрипта
        // BufferedReader bufferedReader = new BufferedReader(
        //     new InputStreamReader(
        //         demoProcess.getInputStream()
        //     )
        // );

        // String outputLine = "";
        // outputLine = bufferedReader.readLine();

        // if (!outputLine.equals("end")) {
        //     throw new Exception();
        // }
        
        taskToProcService.updateTaskStatus(task, TaskToProcStatus.FINISHED);
        zipService.zipDirectory(
            taskToProcFilesService.getOutputFolderForTask(task)
            , taskToProcFilesService.getWorkFolderForTask(task)
            , taskToProcFilesService.getNameForResultFile(task, false)
        );
    }
}
