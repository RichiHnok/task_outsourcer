package com.richi.richis_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.richi.richis_app.entity.User;
import com.richi.richis_app.service.user_service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequestMapping("/editor")
@SessionAttributes({"currentUser"})
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

    @RequestMapping(value = "/saveUser", method = RequestMethod.POST, params = "save")
    public String saveTaskSample(@ModelAttribute("user") User user, Model model){
        userService.saveUser(user);
        User currentUser = (User) model.getAttribute("currentUser");
        if(user.getId() == currentUser.getId()){
            model.addAttribute("currentUser", user);
        }
        return "redirect:/editor/users";
    }

    @RequestMapping("/updateInfo/user")
    public String updateUser(@RequestParam("userId") int id, Model model){
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
