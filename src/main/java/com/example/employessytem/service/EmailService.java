package com.example.employessytem.service;

import com.example.employessytem.dto.EmailDTO;
import com.example.employessytem.entity.User;
import jakarta.mail.MessagingException;

public interface EmailService {
    public void sendEmail(EmailDTO emailDTO) throws MessagingException;
    public void sendCredentialsEmail(String destination, String email, String password) throws MessagingException;

    void sendVacationsRequestEmails(User user);

    void sendVacationApprovedEmail(User user);

    void sendVacationRejectedEmail(User user);
}
