package com.richi.web_part.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.richi.common.entity.User;
import com.richi.common.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequestMapping("/editor")
public class UserEditorController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String showAllUsers(Model model){
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("users", allUsers);
        return "editor/user/users-editor";
    }
    
    @RequestMapping("/addUser")
    public String addNewUser(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "editor/user/user-info";
    }

    @PostMapping(value = "/saveUser", params = "save")
    public String saveTaskSample(@ModelAttribute User user, Model model){
        userService.saveUser(user);
        return "redirect:/editor/users";
    }

    @RequestMapping("/updateInfo/user")
    public String updateUser(@RequestParam("userId") int id, Model model) throws Exception{
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "editor/user/user-info";
    }

    @RequestMapping("/deleteInfo/user")
    public String deleteUser(@RequestParam("userId") int id){
        userService.deleteUser(id);
        return "redirect:/editor/users";
    }
}
