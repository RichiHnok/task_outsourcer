package com.richi.richis_app.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.richi.richis_app.entity.Role;
import com.richi.richis_app.entity.User;
import com.richi.richis_app.service.RoleService;
import com.richi.richis_app.service.UserService;

@Controller
public class AuthController {
    
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        User user = new User();
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, String roleName, Model model) throws Exception{
        try{
            if(userService.getUserByLogin(user.getLogin()) != null){
                model.addAttribute("error", "Username is already taken");
                return "auth/register";
            }
        }catch(NoSuchElementException e){
            userService.registerUser(user, roleName);
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(){
        return "auth/login";
    }
}
