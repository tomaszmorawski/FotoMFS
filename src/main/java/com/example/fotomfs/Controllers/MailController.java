package com.example.fotomfs.Controllers;

import com.example.fotomfs.Model.Mail;
import com.example.fotomfs.Services.AutoMailingService;
import com.example.fotomfs.Services.MailingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class MailController {

    MailingService mailingService;
    AutoMailingService autoMailingService;


    @Autowired
    public MailController(MailingService mailingService) {
        this.mailingService = mailingService;
    }

    @PostMapping("/send/{userId}/{fileName}")
    public String sendMessageToAdmin (@PathVariable Long userId, @PathVariable String fileName, Mail mail){

        mailingService.addMail(mail);
      autoMailingService.sendMessage(mail.getEmail(), "wybrano zdjęcia", "Dzieki");

        return "redirect:/";

    }

}
