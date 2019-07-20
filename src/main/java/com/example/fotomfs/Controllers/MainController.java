package com.example.fotomfs.Controllers;

import com.example.fotomfs.Model.User;
import com.example.fotomfs.Services.RoleService;
import com.example.fotomfs.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    private UserService userService;
    private RoleService roleService;

    public MainController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/")
    private String showMainPage(Model model) {
        return "photoAdmin";
    }

    @GetMapping("/loginHome")
    private String showLoginPage(Model model) {
        return "loginHome";
    }

    @GetMapping("/addNewUser")
    private String addNewUser(Model model) {
        User newUser = new User();
        newUser.setPassword("randomStringGenerated");
        model.addAttribute("newUser", newUser);
        return "addNewUser";
    }

    @PostMapping("/newuser")
    private String crateNewUser(User newUser, Model model) {
        if (newUser.getLogin().isEmpty() || newUser.getPassword().isEmpty()) {
            model.addAttribute("newUser", newUser);
            return "addNewUser";
        }
        newUser.getRoles().add(roleService.getRoleByName("ROLE_USER"));
        userService.addUser(newUser);
        List<User> userList = userService.getAllUsers();
        model.addAttribute("users", userList);
        return "redirect:/admin";
    }

    @GetMapping("/admin")
    private String adminPage(Model model) {
        List<User> userList = userService.getAllUsers();
        model.addAttribute("users", userList);
        return "admin";
    }

    @GetMapping("/delete/{userId}")
    private String deleteUser(@PathVariable Long userId, Model model){
        userService.deleteUserById(userId);
        return "redirect:/admin";
    }

}
