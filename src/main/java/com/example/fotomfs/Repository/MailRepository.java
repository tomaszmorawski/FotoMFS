package com.example.fotomfs.Repository;

import com.example.fotomfs.Model.Mail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailRepository extends JpaRepository <Mail, String> {
}
