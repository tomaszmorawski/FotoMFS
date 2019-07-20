package com.example.fotomfs.Controllers;

import com.example.fotomfs.Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @GetMapping("/")
    private String showMainPage(Model model) {
        return "photo";
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
        List<User> userList = new ArrayList<>();
        userList.add(newUser);
        model.addAttribute("users", userList);
        return "redirect:/admin";
    }

    @GetMapping("/admin")
    private String adminPage(Model model) {
        return "admin";
    }

}
