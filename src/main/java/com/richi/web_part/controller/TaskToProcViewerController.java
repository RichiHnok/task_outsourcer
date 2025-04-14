package com.richi.web_part.controller;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.richi.common.entity.taskToProc.TaskToProc;
import com.richi.common.service.TaskToProcService;
import com.richi.task_manager.TaskManager;
import com.richi.web_part.dto.controlPanel.ControlPanelDto;
import com.richi.web_part.mapper.TemporaryMapper;

@Controller
@RequestMapping("/controlPanel")
public class TaskToProcViewerController {
    
    private final TaskToProcService taskToProcService;
    private final TaskManager taskManager;
    private final TemporaryMapper mapper;

    public TaskToProcViewerController(
        TaskToProcService taskToProcService
        , TaskManager taskManager
        , TemporaryMapper mapper
    ) {
        this.taskToProcService = taskToProcService;
        this.taskManager = taskManager;
        this.mapper = mapper;
    }

    @GetMapping
    public String showAllTasksToProc(
        Model model
        , @RequestParam("page") Optional<Integer> page
    ) throws Exception{
        int currentPage = page.orElse(1);
        //TODO Запихать количество элементов на странице в нормальную константу
        int pageSize = 15;

        ControlPanelDto controlPanelDto = mapper.createControlPanelDto(
            PageRequest.of(currentPage - 1, pageSize)
        );

        model.addAttribute("controlPanelDto", controlPanelDto);

        return "control-panel/control-panel";
    }

    //TODO Эта штука работает через раз как будто
    @PostMapping("/delete/{taskId}")
    public String deleteTaskToProc(
        @PathVariable(name = "taskId") Integer taskId
    ) throws Exception{
        taskToProcService.deleteTaskToProc(taskId);
        return "redirect:/controlPanel";
    }

    @PostMapping("/launch/{taskId}")
    public String handLaunchTask(
        @PathVariable(name = "taskId") Integer taskId
    ) throws Exception{
        // Пока отключён во view-шке
        TaskToProc task = taskToProcService.getTaskToProc(taskId);
        taskManager.doTask(task);
        return "redirect:/controlPanel";
    }

    @PostMapping("/stop/{taskId}")
    public String handStopTask(
        @PathVariable(name = "taskId") Integer taskId
    ){
        return "redirect:/controlPanel";
    }
}
