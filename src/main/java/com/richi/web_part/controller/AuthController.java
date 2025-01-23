package com.richi.web_part.controller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.richi.common.entity.User;
import com.richi.common.service.UserService;

@Controller
public class AuthController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(
        @ModelAttribute User user
        // , String roleName
        , Model model
    ) throws Exception{
        try{
            if(userService.getUserByLogin(user.getLogin()) != null){
                model.addAttribute("error", "Username is already taken");
                return "auth/register";
            }
        }catch(NoSuchElementException e){
            userService.registerUser(user);
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(){
        return "auth/login";
    }
}
