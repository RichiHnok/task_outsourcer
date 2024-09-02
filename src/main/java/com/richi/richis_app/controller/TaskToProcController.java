package com.richi.richis_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.richi.richis_app.entity.TaskToProc;
import com.richi.richis_app.service.task_to_proc_service.TaskToProcService;

@Controller
@RequestMapping("/editor")
public class TaskToProcController {
    
    @Autowired
    private TaskToProcService taskToProcService;

    @RequestMapping("/tasksToProc")
    public String showAllTasksToProc(Model model){
        List<TaskToProc> tasksToProc = taskToProcService.getAllTasksToProc();
        model.addAttribute("tasksToProc", tasksToProc);
        return "editor/task-to-proc/task-to-proc-editor";
    }
}
