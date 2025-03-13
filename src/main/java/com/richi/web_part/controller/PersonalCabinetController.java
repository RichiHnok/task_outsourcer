package com.richi.web_part.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.richi.common.entity.TaskToProc;
import com.richi.common.entity.User;
import com.richi.common.enums.TaskToProcStatus;
import com.richi.common.service.TaskToProcService;
import com.richi.common.service.UserService;

@Controller
public class PersonalCabinetController {

	private final TaskToProcService taskToProcService;
	private final UserService userService;

	public PersonalCabinetController(
		TaskToProcService taskToProcService
		, UserService userService
	) {
		this.taskToProcService = taskToProcService;
		this.userService = userService;
	}

	@GetMapping("/personal")
	public String showPersonalCabinet(
		Model model
		, @RequestParam("page") Optional<Integer> page
		// , @RequestParam("size") Optional<Integer> size
		, @AuthenticationPrincipal UserDetails userDetails
	) throws Exception{
		User currentUser = userService.getUserByLogin(userDetails.getUsername());


		int currentPage = page.orElse(1);
		//TODO Запихать количество элементов на странице в нормальную константу
		// int pageSize = size.orElse(5);
		int pageSize = 15;

		Page<TaskToProc> taskPage = taskToProcService.getTaskToProcsByUser(
			currentUser
			, PageRequest.of(currentPage - 1, pageSize)
		);

		// TODO Как будто в контроллере модифицировать данные это не ок
		for(TaskToProc task : taskPage){
			if(task.getStatus() == TaskToProcStatus.FINISHED)
				task.setFinished(true);
		}

		model.addAttribute("taskPage", taskPage);

		int totalPages = taskPage.getTotalPages();
		if(totalPages > 0){
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
				.boxed()
				.toList();
			model.addAttribute("pageNumbers", pageNumbers);
		}

		return "personal";
	}

	//? Это типа вариант без пагинации? //TODO Можно ли разные реализации сделать и всатвлять их по необходимости или мне лень и можно только с пагинацией оставить?
	// public String showPersonalCabinet(
	// 	Model model
	// 	, @AuthenticationPrincipal UserDetails userDetails
	// ) throws Exception{
	// 	User currentUser = userService.getUserByLogin(userDetails.getUsername());
	// 	List<TaskToProc> tasksListOfCurrentUser = taskToProcService.getTaskToProcsByUser(currentUser);
	// 	// TODO Как будто в контроллере модифицировать данные это не ок
	// 	for(TaskToProc task : tasksListOfCurrentUser){
	// 		taskToProcFilesService.setUriToDownloadResult(task);
	// 	}
	// 	model.addAttribute("tasksToProc", tasksListOfCurrentUser);
	// 	return "personal";
	// }
}
