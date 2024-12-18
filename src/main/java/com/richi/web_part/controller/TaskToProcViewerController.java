package com.richi.web_part.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.richi.common.entity.TaskToProc;
import com.richi.common.service.TaskToProcService;

@Controller
@RequestMapping("/editor")
public class TaskToProcViewerController {
    
    @Autowired
    private TaskToProcService taskToProcService;

    @RequestMapping("/tasksToProc")
    public String showAllTasksToProc(Model model){
        List<TaskToProc> tasksToProc = taskToProcService.getAllTasksToProc();
        // System.out.println(tasksToProc);
        model.addAttribute("tasksToProc", tasksToProc);
        // return "placeholder";
        return "editor/task-to-proc/task-to-proc-editor";
    }
}
