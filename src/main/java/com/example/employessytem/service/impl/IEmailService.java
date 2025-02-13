package com.example.employessytem.service.impl;

import com.example.employessytem.dto.EmailDTO;
import com.example.employessytem.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IEmailService implements EmailService {
    private final JavaMailSender javaMailSender;


    @Override
    public void sendEmail(EmailDTO emailDTO) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        String htmlContent = "<html><body>"
                + "<h1>" + emailDTO.subject() + "</h1>"
                + "<p>" + emailDTO.body() + "</p>"
                + "</body></html>";

        helper.setTo(emailDTO.destination());
        helper.setSubject(emailDTO.subject());
        helper.setText(htmlContent, true);

        javaMailSender.send(message);
    }

    @Override
    public void sendCredentialsEmail(String destination, String email, String password) throws MessagingException {
        String subject = "Your account has been created";
        String body = "Your account has been created with the following credentials: <br>"
                + "<b>Email:</b> " + email + "<br>"
                + "<b>Password:</b> " + password + "<br>"
                + "Please change your password after logging in.";
        EmailDTO emailDTO = new EmailDTO(destination, subject, body);
        sendEmail(emailDTO);
    }
}
