package com.quizapp.quizapp.quiz.email.service;

import com.quizapp.quizapp.quiz.email.utils.EmailTemplateName;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendEmail(
            EmailTemplateName emailTemplate,
            String to,
            String assessmentDate,
            String assessmentTime,
            String assessmentDuration,
            String password,
            String subject
    ) throws MessagingException, UnsupportedEncodingException {
        String templateName;
        if (emailTemplate == null) {
            templateName = "confirm-email";
        } else {
            templateName = emailTemplate.name();
        }
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MULTIPART_MODE_MIXED,
                UTF_8.name()
        );




        Map<String, Object> properties = new HashMap<>();
        properties.put("username", to);
        properties.put("assessmentDate", assessmentDate);
        properties.put("password", password);
        properties.put("assessmentTime", assessmentTime);
        properties.put("assessmentDuration", assessmentDuration);


        Context context = new Context();
        context.setVariables(properties);

//        helper.setFrom("no-reply@navigatelabsai.com");
        String senderName = "Navigate Labs AI";
        helper.setFrom(new InternetAddress("no-reply@navigatelabsai.com", senderName));

        helper.setTo(to);
        helper.setSubject(subject);

        String template = templateEngine.process(templateName, context);

        helper.setText(template, true);

        mailSender.send(mimeMessage);
        log.info("Email sent successfully to the user: {}", to);
    }
}