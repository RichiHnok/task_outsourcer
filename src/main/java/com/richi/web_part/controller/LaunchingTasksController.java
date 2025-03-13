package com.richi.web_part.controller;

import java.time.LocalDateTime;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.richi.common.dto.TaskToProcValues;
import com.richi.common.entity.TaskSample;
import com.richi.common.entity.TaskToProc;
import com.richi.common.entity.User;
import com.richi.common.service.FileFolderManipulationService;
import com.richi.common.service.StorageService;
import com.richi.common.service.TaskToProcService;
import com.richi.common.service.UserService;
import com.richi.task_manager.TaskManager;
import com.richi.common.service.TaskSampleService;

@Controller
public class LaunchingTasksController {

    private Logger log = LoggerFactory.getLogger(LaunchingTasksController.class);

    private final TaskSampleService taskSampleService;
    private final UserService userService;
    private final TaskToProcService taskToProcService;
    private final FileFolderManipulationService fileFolderManipulationService;
    //! private final StorageService storageService;
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
        //! this.storageService = storageService;
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
        
        // TaskValues taskValues = new TaskValues(taskSample);
        // model.addAttribute("taskValues", taskValues);
        
        TaskToProcValues taskValues = new TaskToProcValues(taskSample);
        model.addAttribute("taskValues", taskValues);
        return "tasks/launching-task";
    }

    @RequestMapping("/task/{taskSampleId}/start")
    public String startProcessingTask(
        Model model
        , @PathVariable("taskSampleId") int taskSampleId
        //! , @RequestParam("file") MultipartFile file
        , @ModelAttribute("taskValues") TaskToProcValues values
        , @AuthenticationPrincipal UserDetails userDetails
    ) throws Exception
    {
        TaskSample taskSample = taskSampleService.getTaskSample(taskSampleId);

        model.addAttribute("taskSample", taskSample);
        model.addAttribute("taskValues", values);

        User currentUser = (User) userService.getUserByLogin(userDetails.getUsername());

        TaskToProc task = new TaskToProc(
            taskSample
            , currentUser
            , LocalDateTime.now()
        );

       fileFolderManipulationService.createInputOutputFoldersForTask(task);

       String chekingParamsLine = taskToProcService.saveValuesAndPutThemIntoTaskToProc(task, values);
       log.info("Cheking params line: " + chekingParamsLine);
        // task = taskToProcService.saveTaskToProc(task);

        //! TODO я здесь поставил проверку на нулл для тестирования, наверное
        //! if(!file.isEmpty()){
        //!     storageService.storeInFolder(file, taskToProcFilesService.getInputFolderForTask(task));
        //! }

        // Кидаем задачу TaskManager-у
        // taskManager.addTaskToQuee(task);
        
        return "tasks/task-launched-info";
    }
}
