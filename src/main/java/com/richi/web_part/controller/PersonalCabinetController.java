package com.richi.web_part.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.richi.common.entity.TaskToProc;
import com.richi.common.entity.User;
import com.richi.common.service.TaskToProcService;
import com.richi.common.service.UserService;
import com.richi.web_part.configuration.MyUserDetails;

@Controller
// @SessionAttributes({"currentUser"})
public class PersonalCabinetController {

	@Autowired TaskToProcService taskToProcService;
	@Autowired UserService userService;

	@GetMapping("/personal")
	public String showPersonalCabinet(
		Model model
		, @AuthenticationPrincipal UserDetails userDetails
	) throws Exception{

		// MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// MyUserDetails myUserDetails = (MyUserDetails) userDetails;
		// System.out.println("\n\n\n\n");
		// System.out.println(userDetails.getUsername());
		// System.out.println("\n\n\n\n");

		User currentUser = userService.getUserByLogin(userDetails.getUsername());
		List<TaskToProc> tasksListOfCurrentUser = taskToProcService.geTaskToProcsByUser(currentUser);
		model.addAttribute("user", currentUser);
		model.addAttribute("tasksToProc", tasksListOfCurrentUser);
		return "personal";
	}
}
