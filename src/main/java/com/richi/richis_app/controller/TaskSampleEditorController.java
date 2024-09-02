package com.richi.richis_app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.richi.richis_app.entity.TaskSample;
import com.richi.richis_app.entity.TaskSampleParam;
import com.richi.richis_app.functions.RandomString;
import com.richi.richis_app.service.storage_service.StorageService;
import com.richi.richis_app.service.task_sample_service.TaskSampleService;

@Controller
@RequestMapping("/editor")
public class TaskSampleEditorController {
    
    @Autowired
    private TaskSampleService taskSampleService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private RandomString randomString;
    
    @GetMapping("/taskSamples")
    public String showAllTaskSamples(Model model){

        List<TaskSample> allTaskSamples = taskSampleService.getAllTaskSamples();
        model.addAttribute("taskSamples", allTaskSamples);
        return "editor/task-sample/task-samples-editor";
    }

    @RequestMapping("/addTaskSample")
    public String addNewTaskSample(Model model){
        TaskSample taskSample = new TaskSample();
        model.addAttribute("taskSample", taskSample);
        return "editor/task-sample/task-sample-info";
    }

    @PostMapping(value = "/saveTaskSample", params = "save")
    public String saveTaskSample(@ModelAttribute("taskSample") TaskSample taskSample){
        System.out.println("-888-888-8--8-88-8-8  " + taskSample.getScriptFile().getOriginalFilename());
        MultipartFile file = taskSample.getScriptFile();
        
        
        taskSampleService.saveTaskSample(taskSample);
        return "redirect:/editor/taskSamples";
    }

    @RequestMapping(value = "/saveTaskSample", method = RequestMethod.POST, params = "addParam")
    public String addParamToTaskSample(@ModelAttribute("taskSample") TaskSample taskSample, Model model){
        String paramName = "";
        
        while(true){
            paramName = "No name " + (Integer.toString(1000 + (int)(Math.random() * ((9999 - 1000) + 1))));
            if(taskSample.getParams() == null || !taskSample.getParams().stream().map(TaskSampleParam::getName).collect(Collectors.toList()).contains(paramName)){
                break;
            }
        }

        TaskSampleParam param = new TaskSampleParam(paramName);
        taskSample.addParamToTaskSample(param);
        model.addAttribute("taskSample", taskSample);
        return "editor/task-sample/task-sample-info";
    }

    // @RequestMapping(value = "/saveTaskSample", method = RequestMethod.POST, params = "removeParam")
    // public String removeParamFromTaskSample(@ModelAttribute("taskSample") TaskSample taskSample, Model model){
    //     TaskSampleParam param = new TaskSampleParam("No name");
    //     taskSample.addParamToTaskSample(param);
    //     model.addAttribute("taskSample", taskSample);
    //     return "editor/task-sample/task-sample-info";
    // }

    @RequestMapping(value = "/removeParam")
    public String removeParamFromTaskSample(
        @RequestParam("taskSampleParamId") int paramId,
        @RequestParam("taskSampleParamName") String paramName,
        @ModelAttribute("taskSample") TaskSample taskSample,
        Model model
    ){
        taskSample.removeParamFromTaskSample(paramId, paramName);
        model.addAttribute("taskSample", taskSample);
        return "editor/task-sample/task-sample-info";
    }

    @RequestMapping("/updateInfo/taskSample")
    public String updateTaskSample(@RequestParam("taskSampleId") int id, Model model){
        TaskSample taskSample = taskSampleService.getTaskSample(id);
        model.addAttribute("taskSample", taskSample);
        return "editor/task-sample/task-sample-info";
    }

    @RequestMapping("/deleteInfo/taskSample")
    public String deleteTaskSample(@RequestParam("taskSampleId") int id){
        taskSampleService.deleteTaskSample(id);
        return "redirect:/editor/taskSamples";
    }

}
