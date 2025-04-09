package com.richi.task_manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.richi.common.entity.TaskToProc;
import com.richi.common.enums.TaskToProcStatus;
import com.richi.common.service.FileFolderManipulationService;
import com.richi.common.service.TaskToProcService;
import com.richi.common.service.ZipService;

import jakarta.annotation.PreDestroy;

//TODO Сделать возможность остановки пользоватлем своих задач
@Service
public class TaskManager{

    private Logger log = LoggerFactory.getLogger(TaskManager.class);

    private final TaskToProcService taskToProcService;
    private final FileFolderManipulationService fileFolderManipulationService;
    private final ZipService zipService;

    private ExecutorService executorService;
    private int processingThreadsAmount = 3;

    private PriorityQueue<TaskToProc> createdTasks;
    private List<Future<TaskProcessingResult>> processingTasks;

    // Создаём executor service, который будет принмать задачи
    // реализации Callable, в которых будет запускаться скрипт с укзанными параметрами

    public TaskManager(
        TaskToProcService taskToProcService
        , FileFolderManipulationService fileFolderManipulationService
        , ZipService zipService
    ) {
        log.info("Task Manager Constructor in action");
        this.taskToProcService = taskToProcService;
        this.fileFolderManipulationService = fileFolderManipulationService;
        this.zipService = zipService;

        executorService = Executors.newFixedThreadPool(processingThreadsAmount);

        createdTasks = new PriorityQueue<>(
            (task1, task2) -> task1.getUser().getPriority() - task2.getUser().getPriority()
        );
        processingTasks = new CopyOnWriteArrayList<>();

        initTasks();
    }

    @Scheduled(fixedDelay = 10000, initialDelay = 4000)
    private void logStatus(){
        log.info("Task manager updater running. Active threads: "
            + ((ThreadPoolExecutor) executorService).getActiveCount()
            + ". 'Created Tasks' Size: " + createdTasks.size()
        );
    }

    @Scheduled(fixedDelay = 2500, initialDelay = 4000)
    private void updateTaskProcessing(){
        while(
            ((ThreadPoolExecutor) executorService).getActiveCount() < processingThreadsAmount
            && !createdTasks.isEmpty()
        ){
            Future<TaskProcessingResult> newTask;
            synchronized(createdTasks){
                TaskToProc nextTaskToExecute = createdTasks.poll();
                taskToProcService.updateTaskStatus(nextTaskToExecute, TaskToProcStatus.IN_PROCESSING);
                newTask = executorService.submit(
                    new TaskToProcCallable(
                        nextTaskToExecute
                        , fileFolderManipulationService
                        , zipService
                    )
                );
            }
            processingTasks.add(newTask);
        }
        
        // Когда перебираешь элементы нельзя изменять коллекцию
        List<Future<TaskProcessingResult>> toRemove = null;
        for(var task : processingTasks){
            if (task.isDone()) {
                TaskProcessingResult processingResult;
                try {
                    processingResult = task.get();
                    switch (processingResult.endType()) {
                        case "ok":
                            taskToProcService.updateTaskStatus(processingResult.task(), TaskToProcStatus.FINISHED);
                            break;
                        case "cancel":
                            taskToProcService.updateTaskStatus(processingResult.task(), TaskToProcStatus.CANCELED);
                            break;
                        case "error":
                            taskToProcService.updateTaskStatus(processingResult.task(), TaskToProcStatus.ERROR);
                            break;
                    }
                } catch (ExecutionException e) {
                    // TODO Я пока не знаю как это здесь должно быть
                } catch (InterruptedException e){
                    // TODO Я пока не знаю как это здесь должно быть
                }

                toRemove = (toRemove == null) ? new ArrayList<>() : toRemove;
                toRemove.add(task);
            }
        }

        if(toRemove != null){
            processingTasks.removeAll(toRemove);
        }
    }

    private void initTasks(){
        createdTasks.addAll(
            taskToProcService.getTaskWithStatuses(
                Arrays.asList(
                    TaskToProcStatus.CREATED
                    , TaskToProcStatus.IN_PROCESSING
                )
            )  
        );
    }

    @PreDestroy
    public void destroy(){
        log.info("Task Manager predestroyer in action");
        executorService.shutdown();
    }

    public void launchTaskProcessing(){

    }

    public synchronized void addTaskToQuee(TaskToProc task){
        log.info("Adding a task");
        createdTasks.add(task);
    }

    //TODO должен быть приватным методом
    public synchronized void doTask(TaskToProc task) throws Exception{
        TaskToProcCallable taskProcess = new TaskToProcCallable(task, fileFolderManipulationService, zipService);
        taskToProcService.updateTaskStatus(task, TaskToProcStatus.IN_PROCESSING);
        Future<TaskProcessingResult> futureTaskProcessing = executorService.submit(taskProcess);


    //     String taskEndType;
    //     try {
    //         if(futureTaskProcessing.isDone())
    //             taskEndType = futureTaskProcessing.get();
    //     } catch (InterruptedException | CancellationException e){
    //         taskEndType = "canceled";
    //     } catch (ExecutionException e){
    //         taskEndType = "error";
    //     }
        
    //     switch (taskEndType) {
    //         case "ok":
                
    //             break;
    //         case "ok":
                
    //             break;
    //         case "ok":
                
    //             break;
    //     }
    }
}
