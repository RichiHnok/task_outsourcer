package com.richi.richis_app.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.richi.richis_app.entity.TaskSample;
import com.richi.richis_app.entity.TaskToProc;
import com.richi.richis_app.entity.TaskValues;
import com.richi.richis_app.entity.User;
import com.richi.richis_app.service.TaskToProcService;
import com.richi.richis_app.service.UserService;
import com.richi.richis_app.service.storage_service.StorageService;
import com.richi.richis_app.service.task_sample_service.TaskSampleService;

@Controller
@SessionAttributes({"currentUser"})
public class FirstController {

    @Autowired
    private TaskSampleService taskSampleService;
    @Autowired
    private UserService userService;
    @Autowired
    private TaskToProcService taskToProcService;
    @Autowired
    private StorageService storageService;
    
    @RequestMapping("/")
    public String index(Model model, @AuthenticationPrincipal UserDetails userDetails) throws Exception{
        model.addAttribute("serverTime", new Date());
        model.addAttribute("taskSamples", taskSampleService.getAllTaskSamples());
        if(userDetails == null){
            model.addAttribute("currentUser", null);
        }else{
            User user = userService.getUserByLogin(userDetails.getUsername());
            // System.out.println(user);
            model.addAttribute("currentUser", user);
        }
        return "index";
    }

    @RequestMapping("/editor")
    public String goToEditor(){
        return "editor/editor";
    }

    @RequestMapping("/task/{id}")
    public String chooseTaskSample(@PathVariable("id") int id, Model model){
        TaskSample taskSample = taskSampleService.getTaskSample(id);
        model.addAttribute("taskSample", taskSample);
        TaskValues taskValues = new TaskValues(taskSample);
        // System.out.println("\n\n" + taskValues);
        model.addAttribute("taskValues", taskValues);
        return "launching-task";
    }

    @RequestMapping("/task/{id}/start")
    public String startProcessingTask(@PathVariable("id") int id
        , @RequestParam("file") MultipartFile file
        , @ModelAttribute("taskValues") TaskValues values
        , Model model
    ) throws Exception
    {
        TaskSample taskSample = taskSampleService.getTaskSample(id);

        model.addAttribute("taskSample", taskSample);
        model.addAttribute("taskValues", values);
        TaskToProc task = new TaskToProc(
            taskSample,
            (User) model.getAttribute("currentUser"),
            LocalDateTime.now(),
            values.getValuesAsJoinedString()
        );
        task = taskToProcService.saveTaskToProc(task);

        Path taskInputFolder = taskToProcService.getInputFolderForTask(task.getId());
        if(!taskInputFolder.toFile().mkdirs()){
            throw new Exception("Troubles with creating input folder for task processing");
        }
        Path taskOutputFolder = taskToProcService.getOutputFolderForTask(task.getId());;
        if(!taskOutputFolder.toFile().mkdirs()){
            throw new Exception("Troubles with creating output folder for task processing");
        }
        storageService.storeInFolder(file, taskInputFolder);
        
        return "task-launched-info";
    }

    //TODO deprecated method
    @RequestMapping("/set-user")
    public String setUserInSession(Model model){
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "set-user";
    }

    //TODO deprecated method
    @RequestMapping("/set-user/setting")
    public String ssetUserInSession(@RequestParam int selectedUser, Model model) throws Exception{
        model.addAttribute("currentUser", userService.getUser(selectedUser));
        return "redirect:/";
    }
}
