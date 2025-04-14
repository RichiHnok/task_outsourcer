package com.richi.web_part.controller;

import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.richi.common.entity.User;
import com.richi.common.service.UserService;
import com.richi.web_part.dto.personalCabinet.PersonalCabinetDto;
import com.richi.web_part.mapper.TemporaryMapper;

@Controller
public class PersonalCabinetController {

	private final UserService userService;
	private final TemporaryMapper mapper;

	public PersonalCabinetController(
		UserService userService
		, TemporaryMapper mapper
	) {
		this.userService = userService;
		this.mapper = mapper;
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
		// TODO Запихать количество элементов на странице в нормальную константу
		// int pageSize = size.orElse(5);
		int pageSize = 15;
		
		PersonalCabinetDto personalCabinetDto = mapper.createPersonalCabinetDto(
			currentUser
			, PageRequest.of(currentPage - 1, pageSize)
		);

		model.addAttribute("personalCabinetDto", personalCabinetDto);

		return "personal-cabinet";
	}
}
