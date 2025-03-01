package com.richi.task_manager;

import java.util.concurrent.Callable;

import com.richi.common.entity.TaskToProc;
import com.richi.common.service.TaskToProcFilesService;
import com.richi.common.service.ZipService;

//? TODO Надо ли этот класс делать бином типа prototype, чтобы в него spring сам добавлял зависимости или можно обойтись без этого?
//? TODO Если всё-таки надо этот класс делать бином типа prototype, то какова методология работы с такими объектами?
//? TODO Сделать возращаемый код завершения не в виде строки "id type", а в виде отдельного класса с enum-ом?
public class TaskToProcCallable implements Callable<TaskProcessingResult>{

    private TaskToProc task;
    private TaskToProcFilesService taskToProcFilesService;
    private ZipService zipService;
    
    public TaskToProcCallable(
        TaskToProc task
        , TaskToProcFilesService taskToProcFilesService
        , ZipService zipService
    ) {
        this.task = task;
        this.taskToProcFilesService = taskToProcFilesService;
        this.zipService = zipService;
    }

    //? TODO Надо решить, будет ли этот метод также обновлять статус задачи в бд сам или это будет менеджер задач
    //? TODO Как будто бы питоновский скрипт не должен сам сохранять результат, а передавать его как-то java-коду и уже в нём результат должен сохраняться
    //? TODO 
    @Override
    public TaskProcessingResult call()/*  throws Exception  */{
        try {
            String scriptPath = task.getTaskSample().getScriptFilePath().toString();
            ProcessBuilder processBuilder = new ProcessBuilder(
                "python"
                , scriptPath
                , taskToProcFilesService.getOutputFolderForTask(task).toString()
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
                taskToProcFilesService.getOutputFolderForTask(task)
                , taskToProcFilesService.getWorkFolderForTask(task)
                , taskToProcFilesService.getNameForResultFile(task, false)
            );
        } catch (Exception e) {
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
