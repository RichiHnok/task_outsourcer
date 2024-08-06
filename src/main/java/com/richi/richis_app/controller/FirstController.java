package com.richi.richis_app.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.richi.richis_app.entity.TaskSample;
import com.richi.richis_app.entity.TaskValues;
import com.richi.richis_app.service.TaskSampleService;

@Controller
public class FirstController {

    @Autowired
    private TaskSampleService taskSampleService;
    
    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("serverTime", new Date());
        model.addAttribute("taskSamples", taskSampleService.getAllTaskSamples());
        return "index";
    }

    @RequestMapping("/editor")
    public String goToEditor(){
        return "editor";
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
        // System.out.println("taskSample: " + taskSample);
        System.out.println("taskValues: " + values);
        model.addAttribute("taskSample", taskSample);
        model.addAttribute("taskValues", values);
        return "task-launched-info";
    }
}
