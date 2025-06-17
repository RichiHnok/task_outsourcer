package com.richi.web_part.controller;

import java.util.Date;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.richi.common.entity.TaskSample;
import com.richi.common.entity.User;
import com.richi.common.entity.taskToProc.TaskToProc;
import com.richi.common.entity.taskToProc.TaskToProcParam;
import com.richi.common.service.StorageService;
import com.richi.common.service.TaskToProcService;
import com.richi.common.service.UserService;
import com.richi.task_manager.TaskManager;
import com.richi.web_part.mapper.TemporaryMapper;
import com.richi.web_part.dto.launchingTask.LaunchingTaskDto;
import com.richi.web_part.dto.taskProcessingLaunched.TaskProcessingLaunchedDto;
import com.richi.common.service.TaskSampleService;

@Controller
@Validated
public class LaunchingTasksController {

    private final TaskSampleService taskSampleService;
    private final UserService userService;
    private final TaskToProcService taskToProcService;
    private final TaskManager taskManager;
    private final TemporaryMapper mapper;
    
    public LaunchingTasksController(
        TaskSampleService taskSampleService
        , UserService userService
        , TaskToProcService taskToProcService
        , StorageService storageService
        , TaskManager taskManager
        , TemporaryMapper mapper
    ) {
        this.taskSampleService = taskSampleService;
        this.userService = userService;
        this.taskToProcService = taskToProcService;
        this.taskManager = taskManager;
        this.mapper = mapper;
    }

    @RequestMapping("/tasks")
    public String choosingTask(Model model, @AuthenticationPrincipal UserDetails userDetails) throws Exception{
        model.addAttribute("serverTime", new Date()); //TODO Убрать этот serverTime
        model.addAttribute("taskSamples", taskSampleService.getAllTaskSamples());
        return "tasks/choosing-task";
    }

    @RequestMapping("/task/{taskSampleId}")
    public String chooseTaskSample(
        @PathVariable("taskSampleId") int taskSampleId
        , Model model
    ) throws Exception{
        TaskSample taskSample = taskSampleService.getTaskSample(taskSampleId);
        LaunchingTaskDto launchingTaskDto = mapper.createLaunchingTaskDto(taskSample);
        model.addAttribute("launchingTaskDto", launchingTaskDto);

        return "tasks/launching-task";
    }

    @PostMapping("/task/{taskSampleId}/start")
    public String startProcessingTask(
        Model model
        , @PathVariable("taskSampleId") Integer taskSampleId
        , @Validated @ModelAttribute("launchingTaskDto") LaunchingTaskDto launchingTaskDto
        , BindingResult bindingResult
        , @AuthenticationPrincipal UserDetails userDetails
    ) throws Exception
    {
        if(bindingResult.hasErrors()){
            model.addAttribute("launchingTaskDto", launchingTaskDto);
            return "tasks/launching-task";
        }

        TaskSample taskSample = taskSampleService.getTaskSample(taskSampleId);
        User currentUser = userService.getUserByLogin(userDetails.getUsername());

        TaskToProc task = taskToProcService.createTaskToProcWithoutParams(
            taskSample
            , currentUser
        );
        task = taskToProcService.saveTaskToProc(task);

        List<TaskToProcParam> taskParams = mapper.getTaskParamsFromLaunchingTaskDto(
            task
            , launchingTaskDto
        );
        task.setTaskParams(taskParams);
        task = taskToProcService.saveTaskToProc(task);

        //* Кидаем задачу TaskManager-у
        taskManager.addTaskToQuee(task);

        TaskProcessingLaunchedDto taskProcessingLaunchedDto = mapper.createTaskProcessingLaunchedDto(taskSample, task);
        model.addAttribute("taskProcessingLaunchedDto", taskProcessingLaunchedDto);
        
        return "tasks/task-launched-info";
    }
}
