package com.oneworldaccuracy.onboardingservice.service;

import com.oneworldaccuracy.onboardingservice.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String senderEmail, String receiverEmail, String senderName,
                                      String subject, String content) throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(senderEmail, senderName);
        helper.setTo(receiverEmail);
        helper.setSubject(subject);

        helper.setText(content, true);
        mailSender.send(message);
    }
}
