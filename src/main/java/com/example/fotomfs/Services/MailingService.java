package com.example.fotomfs.Services;

import com.example.fotomfs.Model.Mail;
import com.example.fotomfs.Repository.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class MailingService {

    MailRepository mailRepository;

    @Autowired
    public MailingService(MailRepository mailRepository) {
        this.mailRepository = mailRepository;
    }

    public void addMail (Mail mail){
        mailRepository.save(mail);
    }
}
