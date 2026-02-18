package br.com.finance_project.personal_finance_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceIml implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${MAIL_FROM}")
    private String mailFrom;

    @Override
    public Void sendResetCode(String to, String code) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailFrom);
        message.setTo(to);
        message.setSubject("Password Reset Code");
        message.setText("Your reset code is: " + code);

        mailSender.send(message);

        return null;
    }
}
