package com.example.backend_ecommerce.ServiceLayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public void sendMail(String toEmail,String subject,String body)
    {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(senderEmail);

        simpleMailMessage.setTo(toEmail);

        simpleMailMessage.setSubject(subject);

        simpleMailMessage.setText(body);

        javaMailSender.send(simpleMailMessage);
    }
}
