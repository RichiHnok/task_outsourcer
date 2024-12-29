package com.richi.web_part.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.richi.common.entity.User;
import com.richi.common.service.UserService;

@Controller
@SessionAttributes({"currentUser"})
public class PersonalCabinetController {
	
	@Autowired
   private UserService userService;

	@RequestMapping("/personal")
	public String showPersonalCabinet(Model model, @AuthenticationPrincipal UserDetails userDetails) throws Exception{
		if(userDetails == null){
            model.addAttribute("currentUser", null);
        }else{
            User user = userService.getUserByLogin(userDetails.getUsername());
            model.addAttribute("currentUser", user);
        }
		return "personal";
	}
}
