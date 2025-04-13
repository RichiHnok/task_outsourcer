package com.richi.task_manager;

import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.richi.common.entity.taskToProc.TaskToProc;
import com.richi.common.service.FileFolderManipulationService;
import com.richi.common.service.ZipService;

//? TODO Надо ли этот класс делать бином типа prototype, чтобы в него spring сам добавлял зависимости или можно обойтись без этого?
//? TODO Если всё-таки надо этот класс делать бином типа prototype, то какова методология работы с такими объектами?
//? TODO Сделать возращаемый код завершения не в виде строки "id type", а в виде отдельного класса с enum-ом?
public class TaskToProcCallable implements Callable<TaskProcessingResult>{

    private Logger log = LoggerFactory.getLogger(TaskToProcCallable.class);

    private TaskToProc task;
    private FileFolderManipulationService fileFolderManipulationService;
    private ZipService zipService;
    
    public TaskToProcCallable(
        TaskToProc task
        , FileFolderManipulationService fileFolderManipulationService
        , ZipService zipService
    ) {
        this.task = task;
        this.fileFolderManipulationService = fileFolderManipulationService;
        this.zipService = zipService;
    }

    //? TODO Надо решить, будет ли этот метод также обновлять статус задачи в бд сам или это будет менеджер задач
    //? TODO Как будто бы питоновский скрипт не должен сам сохранять результат, а передавать его как-то java-коду и уже в нём результат должен сохраняться
    //? TODO 
    @Override
    public TaskProcessingResult call()/*  throws Exception  */{
        try {
            String launchCommandTemplate = task.getTaskSample().getLaunchCommandTemplate();
            
            List<String> taskParams = task.getTaskParams().stream().map(task -> task.getParamValue()).toList();

            String actualLaunchCommand = String.format(
                launchCommandTemplate
                , Paths.get(task.getTaskSample().getScriptFilePath()).toAbsolutePath().toString()
                , fileFolderManipulationService.getOutputFolderForTask(task).toAbsolutePath().toString()
                , String.join(" ", taskParams)
            );
            // String scriptPath = task.getTaskSample().getScriptFilePath().toString();
            log.info("Starting task with launching command: " + actualLaunchCommand);
            ProcessBuilder processBuilder = new ProcessBuilder(
                actualLaunchCommand.split(" ")
                // beginingOfLaunchCommand
                // , scriptPath
                // , fileFolderManipulationService.getOutputFolderForTask(task).toString()
            ).inheritIO();
    
            Process demoProcess = processBuilder.start();
    
            // taskToProcService.updateTaskStatus(task, TaskToProcStatus.IN_PROCESSING); //* обновление статуса
    
            while(demoProcess.isAlive()){
                if(Thread.currentThread().isInterrupted()){
                    new TaskProcessingResult(task, "cancel");
                }
                Thread.sleep(10);
            }
            
            //TODO// Надо как-то сообразить передачу сообщений о результате выполнения из скрипта
            
            // taskToProcService.updateTaskStatus(task, TaskToProcStatus.FINISHED); //* обновление статуса
            zipService.zipDirectory(
                fileFolderManipulationService.getOutputFolderForTask(task)
                , fileFolderManipulationService.getWorkFolderForTask(task)
                , fileFolderManipulationService.getNameForResultFile(task, false)
            );
        } catch (Exception e) {
            log.error("Error happens during task proccessing :: \n");
            e.printStackTrace();
            return new TaskProcessingResult(task, "error");
        }
        return new TaskProcessingResult(task, "ok");
    }

    public TaskToProc getTask() {
        return task;
    }

    public void setTask(TaskToProc task) {
        this.task = task;
    }
}
