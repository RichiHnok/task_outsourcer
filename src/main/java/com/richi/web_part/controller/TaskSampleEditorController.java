package com.richi.web_part.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.richi.common.entity.TaskSample;
import com.richi.common.enums.TaskSampleParamType;
import com.richi.common.service.TaskSampleService;
import com.richi.web_part.dto.editingTaskSample.EditingTaskSampleDto;
import com.richi.web_part.dto.taskSamplesEditor.TaskSamplesEditorDto;
import com.richi.web_part.mapper.TemporaryMapper;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/editor/taskSamples")
@Validated
public class TaskSampleEditorController {

    private Logger log = LoggerFactory.getLogger(TaskSampleEditorController.class);
    
    private final TaskSampleService taskSampleService;
    private final TemporaryMapper mapper;
    
    public TaskSampleEditorController(
        TaskSampleService taskSampleService
        , TemporaryMapper mapper
    ) {
        log.info("Creating task sample editor controller");
        this.taskSampleService = taskSampleService;
        this.mapper = mapper;
    }


    @GetMapping
    public String showAllTaskSamples(
        Model model
    ){
        List<TaskSample> allTaskSamples = taskSampleService.getAllTaskSamples();
        TaskSamplesEditorDto taskSamplesEditorDto = mapper.createTaskSamplesEditorDto(allTaskSamples);
        model.addAttribute("taskSamplesEditorDto", taskSamplesEditorDto);
        return "editor/task-sample/task-samples-editor";
    }

    @GetMapping("/add")
    public String addNewTaskSample(
        Model model
    ){
        EditingTaskSampleDto editingTaskSampleDto = mapper.createAddingTaskSampleDto();
        model.addAttribute("editingTaskSampleDto", editingTaskSampleDto);
        return "editor/task-sample/task-sample-info";
    }

    @PostMapping(value = "/edit", params = "save")
    public String saveTaskSample(
        Model model
        , @Validated @ModelAttribute("editingTaskSampleDto") EditingTaskSampleDto editingTaskSampleDto
        , BindingResult bindingResult
    ) throws Exception{
        if(bindingResult.hasErrors()){
            model.addAttribute(
                "editingTaskSampleDto"
                , editingTaskSampleDto
            );
            return "editor/task-sample/task-sample-info";
        }

        taskSampleService.saveTaskSample(mapper.getTaskSampleFromEditingTaskSampleDto(editingTaskSampleDto));
        return "redirect:/editor/taskSamples";
    }

    @PostMapping(value = "/edit", params = "addParam")
    public String addParamToTaskSample(
        @ModelAttribute("editingTaskSampleDto") EditingTaskSampleDto editingTaskSampleDto
        , HttpServletRequest request
        , Model model
    ) throws Exception{
        editingTaskSampleDto = mapper.addParamToTaskSample(
            editingTaskSampleDto
            , TaskSampleParamType.valueOf(request.getParameter("addingType"))
        );
        model.addAttribute("editingTaskSampleDto", editingTaskSampleDto);
        return "editor/task-sample/task-sample-info";
    }

    @PostMapping(value = "/edit", params = "removeParam")
    public String removeParamFromTaskSample(
        Model model
        , @RequestParam("paramUuid") String paramUuid
        , @ModelAttribute EditingTaskSampleDto editingTaskSampleDto
    ) throws Exception{
        editingTaskSampleDto = mapper.removeParamFromTaskSample(
            editingTaskSampleDto
            , paramUuid
        );
        model.addAttribute("editingTaskSampleDto", editingTaskSampleDto);
        return "editor/task-sample/task-sample-info";
    }

    @GetMapping("/update/{taskSampleId}")
    public String updateTaskSample(
        @PathVariable("taskSampleId") Integer taskSampleId
        , Model model
    ){
        
        TaskSample taskSample = taskSampleService.getTaskSample(taskSampleId);

        EditingTaskSampleDto editingTaskSampleDto = mapper.createEditingTaskSampleDto(taskSample);
        model.addAttribute("editingTaskSampleDto", editingTaskSampleDto);
        return "editor/task-sample/task-sample-info";
    }

    @RequestMapping("/delete/{taskSampleId}")
    public String deleteTaskSample(
        @PathVariable("taskSampleId") Integer id
    ){
        //TODO Сделать удаление в виде изменения статуса (доп. поля) на "REMOVED". Это нужно для того, чтобы при просмотре tasksToProc не пропадала информация о пользователе.
        taskSampleService.deleteTaskSampleById(id);
        return "redirect:/editor/taskSamples";
    }
}
