package com.oneworldaccuracy.onboardingservice.util;

import net.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

public class Utils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    public static String generateVerificationCode() {
        return RandomString.make(64);
    }

    public static String verificationEmailTemplate() {

        return "Dear #name#,<br>"
                + "Kindly verify your email to complete your registration:<br>"
                + "<h3><a href=\"#url#\" target=\"_self\">verify</a></h3>"
                + "Thank you,<br>"
                + "One World Accuracy.";
    }

    public static String welcomeOnboardEmailTemplate() {

        return "Dear #name#,<br>"
                + "Welcome onboard, thank you for completing your registration on our platform.<br>"
                + "Thank you,<br>"
                + "One World Accuracy.";
    }

    public static String offBoardingEmailTemplate() {

        return "Dear #name#,<br>"
                + "Sorry we're letting you go<br>"
                + "Thank you,<br>"
                + "One World Accuracy.";
    }

    public static String getUrl(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        return url.replace(request.getServletPath(), "");
    }
}
