package com.example.employessytem.service.impl;

import com.example.employessytem.dto.EmailDTO;
import com.example.employessytem.entity.Role;
import com.example.employessytem.entity.User;
import com.example.employessytem.repository.UserRepository;
import com.example.employessytem.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class IEmailService implements EmailService {
  private final JavaMailSender javaMailSender;
  private final UserRepository userRepository;

  @Override
  public void sendEmail(EmailDTO emailDTO) throws MessagingException {
    Assert.notNull(emailDTO, "EmailDTO must not be null");
    Assert.hasText(emailDTO.destination(), "Destination email must not be null or empty");
    Assert.hasText(emailDTO.subject(), "Subject must not be null or empty");
    Assert.hasText(emailDTO.body(), "Body must not be null or empty");

    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

    String htmlContent =
        "<html><body>"
            + "<h1>"
            + emailDTO.subject()
            + "</h1>"
            + "<p>"
            + emailDTO.body()
            + "</p>"
            + "</body></html>";

    helper.setTo(emailDTO.destination());
    helper.setSubject(emailDTO.subject());
    helper.setText(htmlContent, true);

    javaMailSender.send(message);
  }

  @Override
  public void sendCredentialsEmail(String destination, String email, String password)
      throws MessagingException {
    Assert.hasText(destination, "Destination email must not be null or empty");
    Assert.hasText(email, "Email must not be null or empty");
    Assert.hasText(password, "Password must not be null or empty");

    String subject = "Your account has been created";
    String body =
        "Your account has been created with the following credentials: <br>"
            + "<b>Email:</b> "
            + email
            + "<br>"
            + "<b>Password:</b> "
            + password
            + "<br>"
            + "Please change your password after logging in.";
    EmailDTO emailDTO = new EmailDTO(destination, subject, body);
    sendEmail(emailDTO);
  }

  @Override
  public void sendVacationsRequestEmails(User user) {
    List<String> adminAndManagerEmails =
        userRepository.findEmailsByRoles(List.of(Role.ADMIN, Role.MANAGER));

    System.out.println("adminAndManagerEmails: " + adminAndManagerEmails);

    String subject = "New Vacation Request Notification";
    String body =
        "The employee "
            + user.getName()
            + " has requested a vacation. Please review the request in the system.";

    for (String email : adminAndManagerEmails) {
      try {
        System.out.println("Sending email to: " + email);
        sendEmail(new EmailDTO(email, subject, body));
      } catch (MessagingException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void sendVacationApprovedEmail(User user) {
    String subject = "Vacation Request Approved";
    String body = "Congratulations! Your vacation request has been approved. Enjoy your time off!";
    try {
      sendEmail(new EmailDTO(user.getEmail(), subject, body));
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void sendVacationRejectedEmail(User user) {
    String subject = "Vacation Request Rejected";
    String body = "We are sorry to inform you that your vacation request has been rejected.";
    try {
      sendEmail(new EmailDTO(user.getEmail(), subject, body));
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }
}
