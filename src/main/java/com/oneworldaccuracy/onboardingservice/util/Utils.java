package com.oneworldaccuracy.onboardingservice.util;

import net.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

public class Utils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    public static String generateVerificationCode() {
        return RandomString.make(64);
    }

    public static void sendOnBoardingEmail() {
        String toAddress = user.getEmail();
        String fromAddress = "onboarding@onewordaccuracy.com";
        String senderName = "One World Accuracy";
        String subject = "Email Verification";
        String content = "Dear #name#,<br>"
                + "Kindly verify your email to complete your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">verify</a></h3>"
                + "Thank you,<br>"
                + "One World Accuracy.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

    }
}
