package com.example.chat_relex.service;


import com.example.chat_relex.template.Template;
import com.example.chat_relex.template.TemplateEngine;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class EmailSenderService {
    private final JavaMailSender sender;
    private final MailProperties mailProperties;
    private final TemplateEngine templateEngine;

    public void sendTemplate(String toEmail, String subject, Template template, Object model) {
        sendMessage(toEmail, subject, templateEngine.compile(template, model));
    }
    private void sendMessage(String toEmail, String subject, String content) {
        sender.send(mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom(mailProperties.getUsername());
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(content, true);
        });
    }
}