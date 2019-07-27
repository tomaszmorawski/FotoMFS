package com.example.fotomfs.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;



    @Component
    public class AutoMailingService {

        @Autowired
        JavaMailSender javaMailSender;

        public void sendMessage (String to, String subject, String mailBody){
            

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(mailBody);
            javaMailSender.send(simpleMailMessage);
        }
}
