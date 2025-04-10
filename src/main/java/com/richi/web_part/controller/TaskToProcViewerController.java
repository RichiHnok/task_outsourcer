package com.richi.web_part.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.richi.common.entity.TaskToProc;
import com.richi.common.service.TaskToProcService;
import com.richi.task_manager.TaskManager;

@Controller
@RequestMapping("/controlPanel")
public class TaskToProcViewerController {
    
    @Autowired private TaskToProcService taskToProcService;
    @Autowired private TaskManager taskManager;

    @GetMapping
    public String showAllTasksToProc(
        Model model
        , @RequestParam("page") Optional<Integer> page
    ){
        int currentPage = page.orElse(1);
        //TODO Запихать количество элементов на странице в нормальную константу
        int pageSize = 30;

        Page<TaskToProc> taskPage = taskToProcService.getAllTasksToProc(
            PageRequest.of(currentPage - 1, pageSize)
        );

        model.addAttribute("taskPage", taskPage);
        int totalPages = taskPage.getTotalPages();
		if(totalPages > 0){
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
				.boxed()
				.toList();
			model.addAttribute("pageNumbers", pageNumbers);
		}

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
