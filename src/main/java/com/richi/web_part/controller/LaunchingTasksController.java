package com.richi.web_part.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.richi.common.entity.TaskSample;
import com.richi.common.entity.User;
import com.richi.common.entity.taskToProc.TaskToProc;
import com.richi.common.entity.taskToProc.TaskToProcParam;
import com.richi.common.service.FileFolderManipulationService;
import com.richi.common.service.StorageService;
import com.richi.common.service.TaskToProcService;
import com.richi.common.service.UserService;
import com.richi.task_manager.TaskManager;
import com.richi.web_part.dto.taskToProcVal.TaskToProcValueDto;
import com.richi.web_part.dto.taskToProcVal.TaskToProcValuesDto;
import com.richi.common.service.TaskSampleService;

@Controller
public class LaunchingTasksController {

    private Logger log = LoggerFactory.getLogger(LaunchingTasksController.class);

    private final TaskSampleService taskSampleService;
    private final UserService userService;
    private final TaskToProcService taskToProcService;
    private final FileFolderManipulationService fileFolderManipulationService;
    private final TaskManager taskManager;
    
    public LaunchingTasksController(
        TaskSampleService taskSampleService
        , UserService userService
        , TaskToProcService taskToProcService
        , FileFolderManipulationService fileFolderManipulationService
        , StorageService storageService
        , TaskManager taskManager
    ) {
        this.taskSampleService = taskSampleService;
        this.userService = userService;
        this.taskToProcService = taskToProcService;
        this.fileFolderManipulationService = fileFolderManipulationService;
        this.taskManager = taskManager;
    }

    @RequestMapping("/tasks")
    public String choosingTask(Model model, @AuthenticationPrincipal UserDetails userDetails) throws Exception{
        model.addAttribute("serverTime", new Date());
        model.addAttribute("taskSamples", taskSampleService.getAllTaskSamples());
        return "tasks/choosing-task";
    }

    @RequestMapping("/task/{taskSampleId}")
    public String chooseTaskSample(
        @PathVariable("taskSampleId") int taskSampleId
        , Model model
    ){
        TaskSample taskSample = taskSampleService.getTaskSample(taskSampleId);
        model.addAttribute("taskSample", taskSample);
        
        TaskToProcValuesDto taskValues = new TaskToProcValuesDto(taskSample);
        model.addAttribute("taskValues", taskValues);

        return "tasks/launching-task";
    }

    @RequestMapping("/task/{taskSampleId}/start")
    public String startProcessingTask(
        Model model
        , @PathVariable("taskSampleId") int taskSampleId
        , @ModelAttribute("taskValues") TaskToProcValuesDto values
        , @AuthenticationPrincipal UserDetails userDetails
    ) throws Exception
    {
        TaskSample taskSample = taskSampleService.getTaskSample(taskSampleId);

        model.addAttribute("taskSample", taskSample);

        User currentUser = (User) userService.getUserByLogin(userDetails.getUsername());

        TaskToProc task = new TaskToProc(
            taskSample
            , currentUser
            , DateUtils.truncate(new Date(), Calendar.SECOND)
        );
        task = taskToProcService.saveTaskToProc(task);
        
        fileFolderManipulationService.createInputOutputFoldersForTask(task);
        
        List<TaskToProcParam> savedValues = taskToProcService.saveValuesAndPutThemIntoTaskToProc(task, values);

        log.info("Cheking params: " + savedValues.toString());
        task = taskToProcService.saveTaskToProc(task);

        //// Кидаем задачу TaskManager-у
        taskManager.addTaskToQuee(task);

        if(values.getValues() != null){
            for(TaskToProcValueDto val : values.getValues()){
                if(val.getValue() instanceof MultipartFile){
                    val.setValue(((MultipartFile) val.getValue()).getOriginalFilename());
                }
            }
        }
        model.addAttribute("taskValues", values);
        
        return "tasks/task-launched-info";
    }
}
