package com.example.fotomfs.Controllers;

import com.example.fotomfs.Model.Mail;
import com.example.fotomfs.Services.AutoMailingService;
import com.example.fotomfs.Services.MailingService;
import com.example.fotomfs.Services.PhotoService;
import com.example.fotomfs.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.stream.Collectors;

@Controller
public class MailController {

    MailingService mailingService;
    AutoMailingService autoMailingService;
    UserService userService;
    PhotoService photoService;

    public MailController(MailingService mailingService, AutoMailingService autoMailingService, UserService userService, PhotoService photoService) {
        this.mailingService = mailingService;
        this.autoMailingService = autoMailingService;
        this.userService = userService;
        this.photoService = photoService;
    }

    @GetMapping("/send/{userId}")
    public String sendMessageToAdmin(@PathVariable Long userId, Model model) {
        Mail mail = new Mail();
        String body = "Uzytkownik: "+userService.getUserById(userId).getLogin()+" wybrał następujące zdjęcia: ";
        String fileList = photoService.findAllUserPhotoByUserIdAndAreChoice(userId).stream().collect(Collectors.joining());
        autoMailingService.sendMessage(mail.getEmail(), "wybrano zdjęcia", body+fileList);

        return "redirect:/logout";

    }

}
