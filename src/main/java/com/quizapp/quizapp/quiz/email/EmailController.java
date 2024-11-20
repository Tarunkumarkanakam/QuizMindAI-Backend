package com.quizapp.quizapp.quiz.email;

import com.quizapp.quizapp.quiz.email.service.EmailService;
import com.quizapp.quizapp.quiz.email.utils.EmailTemplateName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/sendEmail")
    public String sendEmail(@RequestParam String toEmail, @RequestParam String date, @RequestParam String startTime, @RequestParam String password) throws MessagingException, UnsupportedEncodingException {
        emailService.sendEmail(
                EmailTemplateName.EXAM_USER_REGISTER,
                toEmail,
                date,
                startTime,
                "90 minutes",
                password,
                "Welcome to QuestAI Navigate Labs");
        return "Email sent successfully";
    }

//    @GetMapping("/sendHtmlEmail")
//    public String sendHtmlEmail(@RequestParam String toEmail) {
//        try {
//            emailService.sendHtmlEmail(toEmail, "Test Subject", "<h1>Test Body</h1>");
//            return "HTML Email sent successfully";
//        } catch (MessagingException e) {
//            e.printStackTrace();
//            return "Error sending HTML email: " + e.getMessage();
//        }
//    }
}
