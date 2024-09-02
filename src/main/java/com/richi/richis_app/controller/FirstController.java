package com.richi.richis_app.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.richi.richis_app.entity.TaskSample;
import com.richi.richis_app.entity.TaskToProc;
import com.richi.richis_app.entity.TaskValues;
import com.richi.richis_app.entity.User;
import com.richi.richis_app.service.task_sample_service.TaskSampleService;
import com.richi.richis_app.service.task_to_proc_service.TaskToProcService;
import com.richi.richis_app.service.user_service.UserService;

@Controller
@SessionAttributes({"currentUser"})
public class FirstController {

    @Autowired
    private TaskSampleService taskSampleService;
    @Autowired
    private UserService userService;
    @Autowired
    private TaskToProcService taskToProcService;

    @ModelAttribute("currentUser")
    public User user(){
        return userService.getUser(1);
    }
    
    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("serverTime", new Date());
        model.addAttribute("taskSamples", taskSampleService.getAllTaskSamples());
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
        System.out.println("\n\n" + taskValues);
        model.addAttribute("taskValues", taskValues);
        return "launching-task";
    }

    @RequestMapping("/task/{id}/start")
    public String startProcessingTask(
        @PathVariable("id") int id,
        @ModelAttribute("taskValues") TaskValues values,
        Model model
    ){
        TaskSample taskSample = taskSampleService.getTaskSample(id);
        // System.out.println("taskValues: " + values);
        model.addAttribute("taskSample", taskSample);
        model.addAttribute("taskValues", values);
        TaskToProc task = new TaskToProc(
            LocalDateTime.now(),
            values.getValuesAsJoinedString()
        );
        // TaskToProc task = new TaskToProc(
        //     (User) model.getAttribute("currentUser"),
        //     taskSample,
        //     LocalDateTime.now(),
        //     values.getValuesAsJoinedString()
        // );
        taskToProcService.saveTaskToProc(task);
        return "task-launched-info";
    }

    @RequestMapping("/set-user")
    public String setUserInSession(Model model){
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "set-user";
    }

    @RequestMapping("/set-user/setting")
    public String ssetUserInSession(@RequestParam("selectedUser") int selectedUser, Model model){
        model.addAttribute("currentUser", userService.getUser(selectedUser));
        return "redirect:/";
    }
}
