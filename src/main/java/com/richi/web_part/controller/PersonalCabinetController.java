package com.richi.web_part.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.richi.common.entity.TaskToProc;
import com.richi.common.entity.User;
import com.richi.common.service.TaskToProcService;
import com.richi.common.service.UserService;

@Controller
public class PersonalCabinetController {

	@Autowired TaskToProcService taskToProcService;
	@Autowired UserService userService;

	@GetMapping("/personal")
	public String showPersonalCabinet(
		Model model
		, @AuthenticationPrincipal UserDetails userDetails
	) throws Exception{
		User currentUser = userService.getUserByLogin(userDetails.getUsername());
		List<TaskToProc> tasksListOfCurrentUser = taskToProcService.geTaskToProcsByUser(currentUser);
		model.addAttribute("tasksToProc", tasksListOfCurrentUser);
		return "personal";
	}
}
