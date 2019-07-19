package com.example.fotomfs.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    private String showMainPage(Model model){
        return "photo";
    }

    @GetMapping("/loginHome")
    private String showLoginPage(Model model){
        return "loginHome";
    }
}
